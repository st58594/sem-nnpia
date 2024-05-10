import LogoutButton from "./LogoutButton.jsx";
import PropTypes from "prop-types";

function Restricted({message}) {
    return(
        <div className="text-center my-4">
            <h1>{message}</h1>
            <LogoutButton className={"btn btn-primary my-3"}/>
        </div>
    )
}
Restricted.propTypes = {
    message: PropTypes.string.isRequired
}
export default Restricted;
