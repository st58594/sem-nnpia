import {useCallback, useEffect, useState} from "react";
import {toast} from "react-toastify";
import ContractApi from "../apis/Contract.js"

function Contracts() {
    const [contracts, setContracts] = useState([]);

    const fetchContracts = useCallback(async () => {
        try {
            const response = await ContractApi.getAll();
            setContracts(response.data)
        } catch (e) {
            toast.error(e);
        }
    }, []);

    useEffect(() => {
        fetchContracts();
    }, [fetchContracts]);
    return (
        <div className="table-responsive data-grid">
            <table className="table table-bordered table-striped">
                <thead className="table-dark">
                <tr>
                    <th>ID</th>
                    <th>State</th>
                    <th>TotalPrice</th>
                    <th>User</th>
                    <th>products</th>
                </tr>
                </thead>
                <tbody>
                {
                    contracts.map(contract => (
                        <tr key={contract.id}>
                            <td>{contract.id}</td>
                            <td><span className="badge badge-primary">{contract.state}</span></td>
                            <td>{contract.totalPrice}</td>
                            <td>{contract.user.firstName + " " + contract.user.lastName}</td>
                            <td>{contract.contractProducts.map(contractProduct => (
                                <span key={contractProduct.product.id} className="badge badge-primary">{contractProduct.ordered + "x " + contractProduct.product.name}</span>))}
                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
        </div>
    )
}

export default Contracts;