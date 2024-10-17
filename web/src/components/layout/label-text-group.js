import React from 'react';
import {Col, FormGroup, Form, Row} from 'react-bootstrap';

import './label-text-group.css';


export default class LabelTextGroup extends React.Component {

    render() {
        const {
            id,
            label,
            disabled,
            placeholder,
            minlength,
            onChange,
            readonly,
            required,
            value,
            type,
            label_size,
            input_size
        } = this.props;

        return (
            <Row>
                <FormGroup className="text-field">
                    <Col md={label_size}>
                        <Form.Label className="form-label">{label}{required && (
                            <span className="required-field-indicator">*</span>)}:</Form.Label>
                    </Col>
                    <Col md={input_size}>
                        <Form.Control
                            className="text-field"
                            readOnly={readonly}
                            id={id}
                            type={type}
                            value={value}
                            onChange={onChange}
                            minLength={minlength}
                            placeholder={placeholder}
                            disabled={disabled}
                        />
                    </Col>
                </FormGroup>
            </Row>
        );
    }

}
