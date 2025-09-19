import React, {useContext} from 'react';
import './AppHeader.css';
import {Link, NavLink, useNavigate} from "react-router-dom";
import logo from './Design.png';
import {AuthContext} from '../auth/AuthProvider';
import {useSelector} from "react-redux";

const AppHeader = () => {
    const {token, setToken} = useContext(AuthContext); // Use 'token' here
    const navigate = useNavigate();
    const {user, loading} = useSelector(state => state.userReducer);

    const handleLogout = () => {
        localStorage.removeItem("jwtToken");
        setToken("");
        navigate("/signin");
    };

    const logoStyle = {
        width: '50px',
        height: 'auto',
        marginRight: '10px',
    };

    return (
        <header className="header">
            <div className="left-header">
                {token ? (
                    <Link to="/dashboard"><img src={logo} alt="Logo" style={logoStyle}/></Link>
                ) : (
                    <Link to="/signin"><img src={logo} alt="Logo" style={logoStyle}/></Link>
                )}
                {
                    token !== null || token !== undefined ?
                        (<ul className="header-menue">
                            <li className="li-header-my"><NavLink to="/dashboard" className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>#</NavLink></li>
                            <li className="li-header-my"><NavLink to="/books"     className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>Books</NavLink></li>
                            <li className="li-header-my"><NavLink to="/tools"     className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>Tools</NavLink></li>
                            <li className="li-header-my"><NavLink to="/places"    className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>Places</NavLink></li>
                            <li className="li-header-my"><NavLink to="/pdfbook"    className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>Pdf books</NavLink></li>
                        </ul>) : ''}
            </div>
            <div className="header-title"><span>Things</span></div>
            <div className="right-header">
                <ul className="header-menue">
                    <li>{loading ? "Loading..." : user.username || "Guest"}</li>
                </ul>
                {token ? (
                    <button className="th-main-button" onClick={handleLogout}>
                        Sign Out
                    </button>
                ) : (
                    <button className="th-main-button" onClick={handleLogout}>Sign In</button>
                )}
            </div>
        </header>
    );
};

export default AppHeader;