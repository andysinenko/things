import React from 'react';
import UserManagement from "./user-management/user-management";
import './admin.css';
import {Tab, Tabs} from "react-bootstrap";
import UserChange from "./user-change/user-change";
import UserRights from "./user-rights/user-rights";

export default class Admin extends React.Component {

    render() {
        return (
            <div className="Container">
                <Tabs defaultActiveKey="home" id="fill-tab-example" className="mb-4" fill>
                    <Tab eventKey="home" title="Add user" >
                        <UserManagement/>
                    </Tab>
                    <Tab eventKey="contact" title="Change User">
                        <UserChange/>
                    </Tab>
                    <Tab eventKey="profile" title="Grant right">
                        <UserRights/>
                    </Tab>
                    <Tab eventKey="longer-tab" title="Delete User">
                        Tab content for Delete Tab
                    </Tab>

                </Tabs>
            </div>
        );
    }
}