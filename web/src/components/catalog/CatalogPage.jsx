import {NavLink, Outlet} from "react-router-dom";
import React from "react";
import './Catalog.css'

const CatalogPage = () => {
    return (
        <div className="catalog-page">
            <nav className="catalog-tabs">
                <ul className="left-catalog-tabs">
                    <li className="li-header-my">
                        <NavLink to="" className="pending" >
                            Catalog:
                        </NavLink>
                    </li>
                    <li className="li-header-my">
                        <NavLink to="/catalog/authors"     className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>
                            Authors
                        </NavLink>
                    </li>
                    <li className="li-header-my">
                        <NavLink to="/catalog/series" className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>
                            Series
                        </NavLink>
                    </li>
                    <li className="li-header-my">
                        <NavLink to="/catalog/genres" className={({ isActive, isPending }) =>  isPending ? "pending" : isActive ? "active_link" : ""}>
                            Genres
                        </NavLink>
                    </li>
                </ul>
            </nav>

            <div className="catalog-content">
                <Outlet className="main-"/>
            </div>
        </div>
    );
}

export default CatalogPage;