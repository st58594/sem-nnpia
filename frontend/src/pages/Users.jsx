import UserApi from "../apis/User.js"
import {useCallback, useEffect, useState} from "react";
import {toast} from "react-toastify";

function Users() {
    const [data, setData] = useState([]);

    const fetchUsers = useCallback(async () => {
        try {
            const response = await UserApi.getAll();
            setData(response.data)
        } catch (e) {
            toast.error(e);
        }
    }, []);

    useEffect(() => {
        fetchUsers();
    }, [fetchUsers]);

    return (
        <div className="table-responsive">
            <table className="table table-bordered table-striped">
                <thead className="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>User name</th>
                        <th>Email</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Roles</th>
                    </tr>
                </thead>
                <tbody>
                {
                    data.map(user => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.username}</td>
                            <td>{user.email}</td>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.roles.map(role => (<span className={"badge badge-primary"} key={role.id}>{role.role}</span>))}
                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
        </div>
    )
}

export default Users;