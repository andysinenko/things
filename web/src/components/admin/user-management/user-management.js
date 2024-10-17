import React from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import LabelTextGroup from "../../layout/label-text-group";
import './user-management.css';



export default class UserManagement extends React.Component {

    render() {
        return (
            <div className="Container user-mgmnt">
                <Row>
                    <Form >
                        <LabelTextGroup type="text" label="User name" placeholder="" label_size={1} input_size={3} minlength={2}/>
                        <LabelTextGroup type="password" label="Password" placeholder="" label_size={1} input_size={3} minlength={6}/>
                        <LabelTextGroup type="text" label="First name" placeholder="" label_size={1} input_size={3} minlength={6}/>
                        <LabelTextGroup type="text" label="Email" placeholder="" label_size={1} input_size={3} minlength={6}/>
                        <LabelTextGroup type="text" label="Last name" placeholder="" label_size={1} input_size={3} minlength={6}/>
                        <LabelTextGroup type="text" label="Phone number" placeholder="" label_size={1} input_size={3} minlength={6}/>
                        <Form.Group as={Row} className="mb-3">
                            <Col sm={{ span: 10, offset: 2 }}>
                                <Button variant="primary" type="submit">Create</Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Row>
            </div>
        );
    }
}