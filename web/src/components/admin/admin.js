import React from 'react';
import UserManagement from "./user-management/user-management";
import './admin.css';
import {Tab, Tabs} from "react-bootstrap";

export default class Admin extends React.Component {

    render() {
        return (
            <div className="Container">
                <Tabs defaultActiveKey="home" id="fill-tab-example" className="mb-4" fill>
                    <Tab eventKey="home" title="Add user">
                        <UserManagement/>
                    </Tab>
                    <Tab eventKey="profile" title="Grant right">
                        Tab content for Profile
                    </Tab>
                    <Tab eventKey="longer-tab" title="Delete User">
                        Tab content for Loooonger Tab
                    </Tab>
                    <Tab eventKey="contact" title="Change User">
                        Tab content for Contact
                    </Tab>
                </Tabs>
            </div>
        );
    }
}