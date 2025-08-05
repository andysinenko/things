import React from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import './user-rights.css';


export default class UserRights extends React.Component {
    render() {
        return (
            <div className="root user-change">
                <Col>
                    <Row md={5}>
                        <Form.Select aria-label="Select user" size="sm">
                            <option>Select user</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                        </Form.Select>
                    </Row>
                    <br/>
                    <Row md={5}>
                        <Form.Select aria-label="Grant right" size="sm">
                            <option>Grant right</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                        </Form.Select>
                    </Row>
                    <br/>
                    <div className="text-center button-container">
                        <Button className="w-25" variant="primary" type="submit">Assign</Button>
                    </div>
                </Col>
            </div>
        );
    }
}