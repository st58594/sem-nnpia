import {useCallback, useEffect, useState} from "react";
import {toast} from "react-toastify";
import ProductApi from "../apis/Product.js"
import Pagination from "../components/Pagination.jsx";
import FilterField from "../components/FilterField.jsx";
import SortField from "../components/SortField.jsx";

function Products() {
    const [loading, setLoading] = useState(true)
    const [products, setProducts] = useState([]);
    const [filter, setFilter] = useState({
        name: '',
        fromPrice: '',
        toPrice: '',
        fromInStock: '',
        toInStock: ''
    })
    const [sort, setSort] = useState([])
    const [page, setPage] = useState(0)
    const [pageSize, setPageSize] = useState(10)
    const [pageCount, setPageCount] = useState(0)


    const fetchProducts = useCallback(async () => {
        try {
            let sortString = '';
            sort.map((item, index) => {
                if (index > 0)
                    sortString += ','
                if (item.key)
                    sortString += item.key + ',' + item.direction
            })
            const response = await ProductApi.getAll(page, pageSize, filter, sortString);
            if (response.data?.content) {
                setProducts(response.data.content)
            }
            if (response.data?.totalPages) {
                setPageCount(response.data.totalPages)
            }
        } catch (e) {
            toast.error(e);
        }
        setLoading(false)
    }, [page, pageSize, filter, sort]);

    useEffect(() => {
        setPage(0)
    }, [pageSize, filter, sort]);

    useEffect(() => {
        fetchProducts()
    }, [fetchProducts]);

    if (loading) {
        return;
    }
    const handlePageClick = (event) => {
        setPage(event.selected);
    };

    const handleFilter = (event, prop) => {
        setFilter({...filter, [prop]: event.target.value});
    }

    return (
        <div className="table-responsive data-grid">
            <table className="table table-bordered table-striped">
                <thead className="table-dark">
                <tr>
                    <th>ID</th>
                    <SortField label={"Name"} prop={"name"} setSort={setSort} sort={sort}/>
                    <SortField label={"Price"} prop={"price"} setSort={setSort} sort={sort}/>
                    <SortField label={"In Stock"} prop={"inStock"} setSort={setSort} sort={sort}/>
                </tr>
                <tr>
                    <th></th>
                    <th>
                        <FilterField placeholder={"product name"} value={filter.name}
                                     onChange={event => handleFilter(event, "name")}/>
                    </th>
                    <th>
                        <FilterField type={"number"} placeholder={"from"} value={filter.fromPrice}
                                     onChange={event => handleFilter(event, "fromPrice")}/>
                        <FilterField type={"number"} placeholder={"up to"} value={filter.toPrice}
                                     onChange={event => handleFilter(event, "toPrice")}/>
                    </th>
                    <th>
                        <FilterField type={"number"} placeholder={"from"} value={filter.fromInStock}
                                     onChange={event => handleFilter(event, "fromInStock")}/>
                        <FilterField type={"number"} placeholder={"up to"} value={filter.toInStock}
                                     onChange={event => handleFilter(event, "toInStock")}/>
                    </th>
                </tr>
                </thead>
                <tbody>
                {
                    products.map(product => (
                        <tr key={product.id}>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.price}</td>
                            <td>{product.inStock}</td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
            <div className="data-grid-footer">
                <Pagination pageCount={pageCount} handlePageClick={handlePageClick} page={page}/>
                <div className="form-group">
                    <input className="form-control" step={10} type="number" value={pageSize} onChange={event=>setPageSize(event.target.value)}/>
                </div>
            </div>
        </div>
    )
}

export default Products;