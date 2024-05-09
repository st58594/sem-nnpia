import './styles/App.scss'
import {Suspense} from "react";
import {Outlet} from "react-router-dom"

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {

    return (
        <Suspense fallback="loading">
            <div id = "app">
                <Outlet />
                <ToastContainer
                    position="bottom-right"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss={false}
                    draggable
                    pauseOnHover
                    theme="colored"
                    transition: Bounce/>
            </div>
        </Suspense>
    );
}

export default App
