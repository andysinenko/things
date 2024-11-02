import React from 'react';
import {Button, Form, Row} from "react-bootstrap";
import LabelTextGroup from "../../layout/input_group/label-text-group";
import './user-creation.css';

export default class UserCreation extends React.Component {

    render() {
        return (
            <div className="Container user-creation">
                <Row>
                    <Form>
                        <Form.Check type="switch" id="enabled-switch" label="Enabled" defaultChecked={true}/>
                        <br/>
                        <LabelTextGroup type="text" label="User name" placeholder="" label_size={1} input_size={3}
                                        minlength={2}/>
                        <LabelTextGroup type="password" label="Password" placeholder="" label_size={1} input_size={3}
                                        minlength={6}/>
                        <LabelTextGroup type="text" label="First name" placeholder="" label_size={1} input_size={3}
                                        minlength={2}/>
                        <LabelTextGroup type="text" label="Last name" placeholder="" label_size={1} input_size={3}
                                        minlength={2}/>
                        <LabelTextGroup type="text" label="Email" placeholder="" label_size={1} input_size={3}
                                        minlength={2}/>
                        <LabelTextGroup type="text" label="Phone number" placeholder="" label_size={1} input_size={3}
                                        minlength={0}/>
                        <br/>
                        <div className="container">
                            <Button variant="primary" type="submit">Create</Button>
                        </div>
                    </Form>
                </Row>
            </div>
        );
    }
}