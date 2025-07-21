import React from 'react';
import {Col, FormGroup, Form, Row} from 'react-bootstrap';

import classes from "./label-text-group.module.css";


export default class LabelTextGroup extends React.Component {
    state = {
        border: this.props.minlength === 0 ? "black" : "red",
        borderShadow: "rgba(13, 110, 253, 0.25) 0px 0px 0px 0.25rem"
    };

    onChange = (event) => {
        if(!this.props.readonly) {
            let length = event.target.value.length;
            if (length > 0 && length < this.props.minlength) {
                this.setState((state) => {
                    return {
                        border: "red"
                    }
                });
            } else {
                this.setState((state) => {
                    return {
                        border: "black"
                    }
                });
            }
        }

        if (this.props.outerOnChange) {
            this.props.outerOnChange(event);
        }
    };

    render() {
        const {
            id,
            label,
            disabled,
            placeholder,
            minlength,
            readonly,
            required,
            value,
            type,
            label_size,
            input_size,
            outerOnChange
        } = this.props;

        let newStyle = {
            border: "2px solid " + this.state.border,
            boxShadow: this.state.borderShadow
        };

        return (
            <Row>
                <FormGroup className={classes.textField}>
                    <Col md={label_size}>
                        <Form.Label className="form-label">{label}{required && (
                            <span className={classes.requiredFieldIndicator}>*</span>)}:</Form.Label>
                    </Col>
                    <Col md={input_size}>
                        <Form.Control
                            className={classes.textField}
                            readOnly={readonly}
                            id={id}
                            type={type}
                            value={value}
                            onChange={this.onChange}
                            minLength={minlength}
                            placeholder={placeholder}
                            disabled={disabled}
                            style={newStyle}
                        />
                    </Col>
                </FormGroup>
            </Row>
        );
    }

}
