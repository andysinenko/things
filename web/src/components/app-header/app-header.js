import React, { useContext } from 'react';
import { Link, useNavigate } from "react-router-dom";
import logo from './Design.png';
import { AuthContext } from '../auth/AuthProvider';

const AppHeader = () => {
    const { token, setToken } = useContext(AuthContext); // Use 'token' here
    const navigate = useNavigate();

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
        <header className="p-3 bg-dark text-white mymargin">
            <div className="Container">
                <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <Link to="/"><img src={logo} alt="Logo" style={logoStyle}/></Link>
                    <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li className="li-header"><Link to="/dashboard" className="nav-link px-2 text-white">#</Link></li>
                        <li className="li-header"><Link to="/books" className="nav-link px-2 text-white">Books</Link></li>
                        <li className="li-header"><Link to="/tools" className="nav-link px-2 text-white">Tools</Link></li>
                        <li className="li-header"><Link to="/places" className="nav-link px-2 text-white">Places</Link></li>
                        <li className="li-header"><Link to="/admin" className="nav-link px-2 text-white">Admin</Link></li>
                    </ul>
                    {token ? (
                        <button className="btn btn-warning" onClick={handleLogout}>
                            Logout
                        </button>
                    ) : (
                        <button className="btn btn-warning">
                            <Link to="/signin" className="nav-link px-2 text-white">SignIn</Link>
                        </button>
                    )}
                </div>
            </div>
        </header>
    );
};

export default AppHeader;