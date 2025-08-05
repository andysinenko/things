import React from 'react';
import {Col, FormGroup, Form, Row} from 'react-bootstrap';
import classes from "./th-select.css";


export default class ThSelect extends React.Component {
    render() {
        const {
            defaultChecked,
            values,
            inputSize,
            label,
            onChange
        } = this.props;

        return (
            <div className="thselect">
                <Form.Select className="thselect" size={inputSize} onChange={onChange}
                             defaultChecked={defaultChecked}>
                    <option key="0">{label}</option>
                    {
                        values.map((value, index) => {
                            return (
                                <option className="thselect-field" key={value.id}
                                        value={value.title}>{value.innerText}</option>
                            );
                        })
                    }
                </Form.Select>
            </div>
        );}

}
