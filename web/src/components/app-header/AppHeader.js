import React, { useContext } from 'react';
import './AppHeader.css';
import { Link, useNavigate } from "react-router-dom";
import logo from './Design.png';
import { AuthContext } from '../auth/AuthProvider';
import {useSelector} from "react-redux";

const AppHeader = () => {
    const { token, setToken } = useContext(AuthContext); // Use 'token' here
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
        <header className="p-3 bg-dark text-white mymargin th-header">
            <div className="Container">
                <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    {token ? (
                        <Link to="/dashboard"><img src={logo} alt="Logo" style={logoStyle}/></Link>
                    ) : (
                        <Link to="/signin"><img src={logo} alt="Logo" style={logoStyle}/></Link>
                    )}
                    <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li className="li-header"><Link to="/dashboard" className="nav-link px-2 text-white">#</Link></li>
                        <li className="li-header"><Link to="/books" className="nav-link px-2 text-white">Books</Link></li>
                        <li className="li-header"><Link to="/tools" className="nav-link px-2 text-white">Tools</Link></li>
                        <li className="li-header"><Link to="/places" className="nav-link px-2 text-white">Places</Link></li>
                        <li className="li-header"><Link to="/admin" className="nav-link px-2 text-white">Admin</Link></li>
                    </ul>

                    <button className="btn-username px-2 text-white">
                        {loading ? "Loading..." : user.username || "Guest"}
                    </button>
                    {token ? (
                        <button className="btn-signout" onClick={handleLogout}>
                            Sign Out
                        </button>
                    ) : (
                        <button className="btn-signout" onClick={handleLogout}>Sign In</button>
                    )}
                </div>
            </div>
        </header>
    );
};

export default AppHeader;