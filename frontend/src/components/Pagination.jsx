import ReactPaginate from "react-paginate";
import PropTypes from "prop-types";

function Pagination ({page, pageCount, handlePageClick}){
    return (
        <ReactPaginate
            forcePage={page}
            pageCount={pageCount}
            onPageChange={handlePageClick}
            nextLabel="&raquo;"
            previousLabel="&laquo;"
            breakLabel="..."
            pageRangeDisplayed={3}
            marginPagesDisplayed={2}
            pageClassName="page-item"
            pageLinkClassName="page-link"
            previousClassName="page-item"
            previousLinkClassName="page-link"
            nextClassName="page-item"
            nextLinkClassName="page-link"
            breakClassName="page-item"
            breakLinkClassName="page-link"
            containerClassName="pagination"
            activeClassName="active "
            renderOnZeroPageCount={null}
        />
    )
}

Pagination.propTypes = {
    page: PropTypes.number.isRequired,
    pageCount: PropTypes.number.isRequired,
    handlePageClick: PropTypes.func.isRequired
}

export default Pagination;