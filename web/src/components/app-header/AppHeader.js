import React, {useContext} from 'react';
import './AppHeader.css';
import {Link, useNavigate} from "react-router-dom";
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
                <ul className="header-menue">
                    <li className="li-header"><Link to="/dashboard" className="nav-link px-2 text-white">#</Link></li>
                    <li className="li-header"><Link to="/books" className="nav-link px-2 text-white">Books</Link></li>
                    <li className="li-header"><Link to="/tools" className="nav-link px-2 text-white">Tools</Link></li>
                    <li className="li-header"><Link to="/places" className="nav-link px-2 text-white">Places</Link></li>
                    <li className="li-header"><Link to="/admin" className="nav-link px-2 text-white">Admin</Link></li>
                </ul>
            </div>
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