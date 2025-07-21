import React from 'react';
import UserCreation from "./user-creation/user-creation";
import './admin.css';
import {Tab, Tabs} from "react-bootstrap";
import UserChange from "./user-change/user-change";
import UserRights from "./user-rights/user-rights";
import UserDelete from "./user-delete/user-delete";

export default class Admin extends React.Component {


    render() {

        return (
            <div className="Container">
               {/* <AppHeader/>*/}
                <div className="main-container">
                <Tabs defaultActiveKey="home" id="fill-tab-example" className="mb-4" fill>
                    <Tab eventKey="home" title="Add user">
                        <UserCreation/>
                    </Tab>
                    <Tab eventKey="contact" title="Change User">
                        <UserChange/>
                    </Tab>
                    <Tab eventKey="profile" title="Grant right">
                        <UserRights/>
                    </Tab>
                    <Tab eventKey="longer-tab" title="Delete User">
                        <UserDelete/>
                    </Tab>
                </Tabs>
                </div>
            </div>
        );
    }
}