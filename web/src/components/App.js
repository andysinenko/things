import './App.css';
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {Outlet} from "react-router-dom";
import {fetchUser} from "./signin/api/api";
import AppHeader from "./app-header";

function App() {
    console.log('App render started');
    const dispatch = useDispatch();

    useEffect(() => {
        console.log('useEffect called on initial mount or refresh');
        const token = localStorage.getItem("jwtToken");
        if (token) {
            fetchUser(dispatch);
        }
    }, [dispatch]);

    return (
        <div className="Container">
            <AppHeader />
            <Outlet />
        </div>
    );
}

export default App;