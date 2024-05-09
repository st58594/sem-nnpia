import PropTypes from "prop-types";

function FilterField({type = "text", placeholder, value, onChange}) {

    return (
        <input className="w-100" type={type} placeholder={placeholder} value={value} onChange={onChange}/>
    );
}
FilterField.propTypes = {
    type: PropTypes.string,
    placeholder: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
}
export default FilterField;