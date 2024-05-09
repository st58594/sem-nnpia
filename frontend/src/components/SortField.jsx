import PropTypes from "prop-types";

function SortField({label, prop, sort, setSort}) {
    const sortBy = () => {
        const newSort = sort.find(item => item.key === prop) || null;

        if (newSort != null) {
            if (newSort.direction === 'asc') {
                newSort.direction = 'desc';
            } else {
                setSort(sort.filter(item=>item.key !== newSort.key))
                return;
            }
            setSort(sort.map(item => {
                if (item.key === newSort.key)
                    return newSort
                return item;
            }))
        } else {
            setSort([...sort, {key: prop, direction:"asc"}])
        }
    };
    const sortIcon = () => {
        const currentSort = sort.find(item => item.key === prop) || null
        if (currentSort != null) {
            if (currentSort.direction === "asc"){
                return (
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-arrow-up" viewBox="0 0 16 16">
                        <path fillRule="evenodd" d="M8 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L7.5 2.707V14.5a.5.5 0 0 0 .5.5"/>
                    </svg>
                )
            } else {
                return (
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-arrow-down" viewBox="0 0 16 16">
                        <path fillRule="evenodd" d="M8 1a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L7.5 13.293V1.5A.5.5 0 0 1 8 1"/>
                    </svg>
                )
            }
        }
        return (
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-dash" viewBox="0 0 16 16">
                <path d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"/>
            </svg>
        );
    }

    return (
        <>
            <th onClick={sortBy}>{label} {sortIcon()}</th>
        </>
    )
}

SortField.propTypes = {
    label: PropTypes.string.isRequired,
    prop: PropTypes.string.isRequired,
    sort: PropTypes.array.isRequired,
    setSort: PropTypes.func.isRequired
}

export default SortField;