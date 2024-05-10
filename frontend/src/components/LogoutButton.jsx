import PropTypes from "prop-types";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {toast} from "react-toastify";
import * as store from '../store/slices/userSlice';

function LogoutButton(className) {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const user = useSelector(state => state.user.user);
    const logout = (e) => {
        e.preventDefault();
        dispatch(store.logout());
        toast.info(user.firstName + " " + user.lastName +" logged out")
        navigate("/login")
    }
    return (
        <button type="button" onClick={logout} className={className.className}>Logout</button>
    )
}
LogoutButton.propTypes = {
    className: PropTypes.string.isRequired
}

export default LogoutButton;