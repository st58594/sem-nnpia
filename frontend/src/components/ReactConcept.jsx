import PropTypes from "prop-types";

function ReactConcept({title, description, image}) {
    return (
        <>
            <h1>{title}</h1>
            <p>{description}</p>
            <img src={image} alt="React"/>
        </>
    );
}

ReactConcept.propTypes = {
    title: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    image: PropTypes.any.isRequired
}

export default ReactConcept;