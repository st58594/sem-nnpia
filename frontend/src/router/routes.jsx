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
                element: <Page title="home" guards={["auth"]}><Home/></Page>
            },
            {
                path: 'users',
                element: <Page title="users" guards={["admin"]}><Users/></Page>
            },
            {
                path: 'products',
                element: <Page title="products" guards={["admin", "product-manager"]}><Products/></Page>
            },
            {
                path: 'contracts',
                element: <Page title="contracts" guards={["admin"]}><Contracts/></Page>
            },
            {
                path: 'login',
                element: <Login/>
            },
        ]
    }
];

export default routes;
