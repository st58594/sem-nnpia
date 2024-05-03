import App from '../App';
import Login from '../pages/Login';
import Page from "../components/Page.jsx";
import Home from "../pages/Home.jsx";
import Users from "../pages/Users.jsx";
import Products from "../pages/Products.jsx";
import Contracts from "../pages/Contracts.jsx";

const routes = [
    {
        path: '/',
        element: <App/>,
        children: [
            {
                path: '',
                element: <Page title="home" guard="auth"><Home/></Page>
            },
            {
                path: 'users',
                element: <Page title="users" guard="auth"><Users/></Page>
            },
            {
                path: 'products',
                element: <Page title="products" guard="auth"><Products/></Page>
            },
            {
                path: 'contracts',
                element: <Page title="contracts" guard="auth"><Contracts/></Page>
            },
            {
                path: 'login',
                element: <Login/>
            },
        ]
    }
];

export default routes;
