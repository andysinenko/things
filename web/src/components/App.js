import './App.css';
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {Outlet} from "react-router-dom";
import AppHeader from "./app-header";
import {fetchUser} from "./signin/reducer/UserSlice";

function App() {
    console.log('App render started');
    const dispatch = useDispatch();

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");
        if (token) {
            dispatch(fetchUser());
        }
    }, []);

    return (
        <div className="root">
            <AppHeader />
            <Outlet />
        </div>
    );
}

export default App;