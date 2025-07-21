import React from "react";
import {Form, Button, Row, Col} from "react-bootstrap";
import "./AddBookModal.css";

const AddBookModal = ({ isOpen, onClose, onSubmit, formData, setFormData, modalType, selectedBook }) => {
    if (!isOpen) return null;

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const renderContent = () => {
        switch (modalType) {
            case "add":
                return (
                    <Row>
                        <Col flex-grow="1" className="modal-col">
                        <h4>Add New Book</h4>
                        <Form onSubmit={onSubmit} className="flex-auto">
                            <Form.Control className="modal-input" type="text" name="id" value={formData.id} onChange={handleChange} placeholder="Enter ID of book"/>
                            <Form.Control className="modal-input" type="text" name="title" value={formData.title} onChange={handleChange} placeholder="Enter title of book"/>
                            <Form.Select size="sm" aria-label="Genre name">
                                <option>Genre name</option>
                                <option value="1">One</option>
                                <option value="2">Two</option>
                                <option value="3">Three</option>
                            </Form.Select>
                            <Form.Select size="sm" aria-label="Author name">
                                <option>Authors</option>
                                <option value="1">AuthorOne</option>
                                <option value="2">AuthorTwo</option>
                                <option value="3">AuthorThree</option>
                            </Form.Select>
                            <Form.Select size="sm" aria-label="Series">
                                <option>Series</option>
                                <option value="1">SeriesOne</option>
                                <option value="2">SeriesTwo</option>
                                <option value="3">SeriesThree</option>
                            </Form.Select>
                            <Form.Select size="sm" aria-label="Publisher">
                                <option>Publisher</option>
                                <option value="1">Publisher1</option>
                                <option value="2">Publisher2</option>
                                <option value="3">Publisher3</option>
                            </Form.Select>
                            <Form.Control className="modal-input" type="text" name="id" value={formData.id} onChange={handleChange} placeholder="Enter year of release"/>

                            <div className="modal-actions">
                                <Button variant="primary" type="submit">
                                    Submit
                                </Button>
                                <Button variant="secondary" onClick={onClose}>
                                    Close
                                </Button>
                            </div>
                        </Form>
                        </Col>
                    </Row>
                );
            case "delete":
                return (
                    <>
                        <h4>Delete Book</h4>
                        <p>Are you sure you want to delete "{selectedBook?.title || "this book"}"?</p>
                        <div className="modal-actions">
                            <Button variant="danger" onClick={onSubmit}>
                                Delete
                            </Button>
                            <Button variant="secondary" onClick={onClose}>
                                Cancel
                            </Button>
                        </div>
                    </>
                );
            case "csv":
                return (
                    <>
                        <h4>Upload CSV</h4>
                        <Form onSubmit={onSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label>Upload CSV File</Form.Label>
                                <Form.Control
                                    type="file"
                                    name="csv"
                                    accept=".csv"
                                    onChange={(e) => setFormData((prev) => ({ ...prev, csv: e.target.files[0] }))}
                                    required
                                />
                            </Form.Group>
                            <div className="modal-actions">
                                <Button variant="primary" type="submit">
                                    Upload
                                </Button>
                                <Button variant="secondary" onClick={onClose}>
                                    Close
                                </Button>
                            </div>
                        </Form>
                    </>
                );
            default:
                return <p>Unknown action</p>;
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">{renderContent()}</div>
        </div>
    );
};

export default AddBookModal;