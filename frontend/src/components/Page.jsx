import Header from './layout/Header';
import '../styles/Page.scss';
import PropTypes from 'prop-types';
import {useEffect} from 'react';
import {useSelector} from 'react-redux';
import {Navigate} from 'react-router-dom';
import {isAdmin} from '../utils';

function Page({title, guard, children}) {
    const user = useSelector(state => state.user.user);

    useEffect(() => {
        document.title = title + ".pageTitle";
    }, [title]);

    if (guard) {
        if (guard === 'auth' && !user) {
            return <Navigate to="/login"/>;
        }
        if (guard === 'guest' && user) {
            return <Navigate to="/"/>;
        }
        if (guard === 'admin' && !isAdmin()) {
            if (user) {
                return <Navigate to="/"/>;
            } else {
                return <Navigate to="/login"/>;
            }
        }
    }

    return (
        <>
            <Header title={title + ".pageTitle"}/>
            <main>
                <div className="card">
                    <div className="card-body">
                        {children}
                    </div>
                </div>
            </main>
        </>
    );
}

Page.propTypes = {
    title: PropTypes.string.isRequired,
    guard: PropTypes.string,
    children: PropTypes.node.isRequired
};

export default Page;
