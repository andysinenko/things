import React from 'react';
import './dashboard.css';
import AppHeader from "../app-header";

export default class Dashboard extends React.Component {
    render() {
        return (
            <div className="Container">
                <AppHeader/>
                <div className="main-container">
                    <h3>Dashboard</h3>
                </div>
            </div>
        );
    };
}