import React from 'react';
import './dashboard.css';
import AppHeader from "../app-header";

export default class Dashboard extends React.Component {
    render() {
        return (
            <div>
                <AppHeader/>
                <table className="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Address</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        );
    };
}