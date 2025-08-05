import React from 'react';
import './user-delete.css';
import {Button, Col, Form, Row} from "react-bootstrap";

export default class UserDelete extends React.Component {
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
                    <div className="text-center button-container">
                        <Button className="w-25" variant="danger" type="submit">Delete</Button>
                    </div>
                </Col>
            </div>
        );
    }
}