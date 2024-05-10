import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {useState} from "react";
import Auth from "../apis/Auth.js";
import * as store from '../store/slices/userSlice';
import {toast} from "react-toastify";

function Login(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const[loading, setLoading] = useState(false);
    const[user, setUser] = useState({
        username: '',
        password: ''
    })

    const login = async (e) =>{
        e.preventDefault()
        setLoading(true)
        try {
            const response = await Auth.login(user)
            dispatch(store.login(response.data))
            navigate("/")
        }catch (e){
            if (e.response?.data){
                toast.error(e.response.data);
            }
        }
        setLoading(false)
    }

    return (
          <>
            <div className="login">
                <h1>Log in</h1>
                <form onSubmit={login}>
                    <fieldset className="my-3" disabled={loading}>
                        <div className="mb-3">
                            <label form="username" className="form-label">User name</label>
                            <input id="username" className="form-control" value={user.username} required type={"text"} onChange={(value) => setUser( {...user, username: value.target.value})}/>
                        </div>
                        <div className="mb-3">
                            <label form="password" className="form-label">Password</label>
                            <input id="password" type="password" className="form-control" value={user.password} required onChange={value=>setUser({...user, password: value.target.value})}/>
                        </div>
                        <input type="submit" className="btn btn-primary" value="Log in"/>
                    </fieldset>
                </form>
            </div>
          </>
    );
}

export default Login;