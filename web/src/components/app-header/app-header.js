import React from 'react';

export default class AppHeader extends React.Component {

    render() {
        return (
            <header className="p-3 bg-dark text-white">
                <div className="Container">
                    <div
                        className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                        <a href="/" className="d-flex align-items-center mb-2 mb-lg-0 text-black text-decoration-none">
                            <svg className="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"></svg>
                        </a>
                        <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                            <li className="li-header"><a href="/" className="nav-link px-2 text-secondary">Home</a></li>
                            <li className="li-header"><a href="/" className="nav-link px-2 text-white">Books</a></li>
                            <li className="li-header"><a href="/" className="nav-link px-2 text-white">Tools</a></li>
                            <li className="li-header"><a href="/" className="nav-link px-2 text-white">Places</a></li>
                            <li className="li-header"><a href="/" className="nav-link px-2 text-white">Admin</a></li>
                        </ul>
                        <div className="text-end">
                            <form className="d-flex">
                                <input className="form-control me-2" type="text" placeholder="Login"/>
                                <input className="form-control me-2" type="password" placeholder="Password"/>

                                <button className="btn btn-warning" type="submit">Login</button>
                            </form>
                        </div>
                    </div>
                </div>
            </header>
        );
    }
}
