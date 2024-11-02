import React from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import LabelTextGroup from "../../layout/input_group/label-text-group";
import './user-change.css';


export default class UserChange extends React.Component {
    render() {
        return (
            <div className="Container user-change">
                <Row md={5} className="selector">
                    <Form.Select aria-label="Select user" size="sm">
                        <option>Select user</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Row>
            <Row>
                <Form >
                    <Form.Check type="switch" id="enabled-switch" label="Enabled" defaultChecked={true}/>
                    <br/>
                    <LabelTextGroup type="text" label="Id" placeholder="" label_size={1} input_size={3} minlength={0} readonly={true}/>
                    <LabelTextGroup type="text" label="User name" placeholder="" label_size={1} input_size={3} minlength={2}/>
                    <LabelTextGroup type="password" label="Password" placeholder="" label_size={1} input_size={3} minlength={6}/>
                    <LabelTextGroup type="text" label="First name" placeholder="" label_size={1} input_size={3} minlength={2}/>
                    <LabelTextGroup type="text" label="Last name" placeholder="" label_size={1} input_size={3} minlength={2}/>
                    <LabelTextGroup type="text" label="Email" placeholder="" label_size={1} input_size={3} minlength={2}/>
                    <LabelTextGroup type="text" label="Phone number" placeholder="" label_size={1} input_size={3} minlength={0}/>
                    <Form.Group as={Row} className="mb-3">
                        <Col sm={{ span: 10, offset: 2 }}>
                            <Button variant="primary" type="submit">Store</Button>
                        </Col>
                    </Form.Group>
                </Form>
            </Row>
        </div>
        );
    }
}