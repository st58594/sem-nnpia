import {Link, useNavigate} from 'react-router-dom';
import '../../styles/Header.scss';
import PropTypes from 'prop-types';
import * as store from '../../store/slices/userSlice';
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import {useState} from "react";

function Header({title}) {
    const navigate = useNavigate()
    const dispatch = useDispatch();
    const user = useSelector(state => state.user.user);
    const [activeLink, setActiveLink] = useState(title);
    const handleNavLinkClick = (link) => {
        setActiveLink(link);
    }

    const logout = (e) => {
        e.preventDefault()
        dispatch(store.logout())
        toast.info(user.firstName + " " + user.lastName + " odhlášen")
        navigate("/login")
    }

    return (
        <header className="p-3 bg-dark text-white">
            <div className="container">
                <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <a href="/" onClick={()=> handleNavLinkClick("home")} className="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <h1 className={activeLink === "home" ? "text-secondary" : ""}>NNPIA</h1>
                    </a>
                    <ul className="nav col-12 col-lg-auto me-lg-auto mx-2 mb-2 justify-content-center mb-md-0">
                        <li>
                            <Link to="/users" onClick={()=>handleNavLinkClick("users")} className={"nav-link px-2 " + (activeLink === "users" ? "text-secondary" : "text-white")}>Uživatelé</Link>
                        </li>
                        <li>
                            <Link to="/products" onClick={()=>handleNavLinkClick("products")} className={"nav-link px-2 " + (activeLink === "products" ? "text-secondary" : "text-white")}>Produkty</Link>
                        </li>
                        <li>
                            <Link to="/contracts" onClick={()=>handleNavLinkClick("contracts")} className={"nav-link px-2 " + (activeLink === "contracts" ? "text-secondary" : "text-white")}>Zakázky</Link>
                        </li>
                    </ul>
                    <div className="text-end">
                        <span className= "p-2">{user && user.firstName +" "+ user.lastName}</span>
                        <button type="button" className="btn btn-outline-light me-2" onClick={logout}>Logout</button>
                    </div>
                </div>
            </div>
        </header>
    );
}

Header.propTypes = {
    title: PropTypes.string.isRequired
};

export default Header;
