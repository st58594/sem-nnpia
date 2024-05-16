import Header from './layout/Header';
import '../styles/Page.scss';
import PropTypes from 'prop-types';
import {useEffect} from 'react';
import {useSelector} from 'react-redux';
import {Navigate} from 'react-router-dom';
import {hasAuthority} from '../utils';
import Restricted from "./Restricted.jsx";

function Page({title, guards, children}) {
    const user = useSelector(state => state.user.user);
    let error = null;

    useEffect(() => {
        document.title = title;
    }, [title]);

    if (!user)
        return <Navigate to="/login"/>;

    guards.forEach(guard => {
        switch (guard) {
            case 'auth':
                break;
            case 'guest':
                error = !hasAuthority("guest") ? {message: "You are not guest"} : null;
                break;
            case 'admin':
                error = !hasAuthority("admin") ? {message: "You are not admin"} : null;
                break;
            case 'product-manager':
                error = !hasAuthority("product-manager") ? {message: "You are not product manager"} : null;
                break;
        }
    })

    return (
        <>
            <Header title={title + ".pageTitle"}/>
            {
                error != null && <Restricted message={error.message}/> ||
                <main>
                    <div className="card">
                        <div className="card-body">
                            {children}
                        </div>
                    </div>
                </main>
            }
        </>
    );
}

Page.propTypes = {
    title: PropTypes.string.isRequired,
    guards: PropTypes.array,
    children: PropTypes.node.isRequired
};

export default Page;
