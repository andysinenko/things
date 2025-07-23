import React from "react";
import {Form, Row, Col} from "react-bootstrap";
import "./AddBookModal.css";

const AddBookModal = ({isOpen, onClose, onSubmit, formData, setFormData, modalType, selectedBook, header, genres, series, authors}) => {
    if (!isOpen) return null;

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData((prev) => ({...prev, [name]: value}));
    };

    const renderContent = () => {
        switch (modalType) {
            case "add":
                return (
                    <Row className=" justify-content-center">
                        <h6 className="text-center">{header}</h6>
                        <Form onSubmit={onSubmit} className="flex-auto">
                            <Form.Control className="modal-input" type="text" name="id" value={formData.id}
                                          onChange={handleChange} placeholder="Enter ID of book"/>
                            <Form.Control className="modal-input" type="text" name="title" value={formData.title}
                                          onChange={handleChange} placeholder="Enter title of book"/>
                            <Form.Select aria-label="Genre name" value={formData.genre} onChange={(e) => setFormData({...formData, genre: e.target.value})}>
                                <option>Genre name</option>
                                {genres.map((genre) => (
                                    <option key={genre.id} value={genre.id}>
                                        {genre.name}
                                    </option>
                                ))}
                            </Form.Select>
                            <Form.Select
                                multiple
                                aria-label="Author name"
                                value={formData.author.map(a => a.id)} // value — массив id
                                onChange={(e) => {
                                    const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.value));
                                    const selectedAuthors = authors.filter(author => selectedIds.includes(author.id));
                                    setFormData({ ...formData, author: selectedAuthors });
                                }}
                            >
                                <option disabled>Authors</option>
                                {authors.map((author) => (
                                    <option key={author.id} value={author.id}>
                                        {author.name}
                                    </option>
                                ))}
                            </Form.Select>
                            <Form.Select aria-label="Series" value={formData.series} onChange={(e) => setFormData({...formData, series: e.target.value})}>
                                {series.map((series) => (
                                    <option key={series.id} value={series.id}>
                                        {series.name}
                                    </option>
                                ))}
                            </Form.Select>
                            <Form.Control className="modal-input" type="text" name="year"
                                          onChange={handleChange} placeholder="Enter year of release" value={formData.year}/>
                            <Form.Control className="modal-input" type="text" name="description"
                                          onChange={handleChange} placeholder="Enter description" value={formData.description}/>

                            <Row className="mb-3 justify-content-center">
                                <Col xs="auto">
                                    <button className="thbtn-add" type="submit">
                                        Save
                                    </button>
                                </Col>
                                <Col xs="auto">
                                    <button className="thbtn-add" type="button" onClick={onClose}>
                                        Cancel
                                    </button>
                                </Col>
                            </Row>
                        </Form>
                    </Row>
                );
            case "delete":
                return (
                    <Row className=" justify-content-center">
                        <h6 className="text-center">Delete book</h6>
                        <Form onSubmit={onSubmit}>
                            <p>Are you sure you want to delete "{selectedBook?.title || "this book"}"?</p>
                            <Row className="mb-3 justify-content-center">
                                <Col xs="auto">
                                    <button className="thbtn-add" type="submit">
                                        Delete
                                    </button>
                                </Col>
                                <Col xs="auto">
                                    <button className="thbtn-add" type="button" onClick={onClose}>
                                        Cancel
                                    </button>
                                </Col>
                            </Row>
                        </Form>
                    </Row>
                );
            case "csv":
                return (
                    <Row className=" justify-content-center">
                        <h6 className="text-center">Upload CSV</h6>
                        <Form onSubmit={onSubmit}>
                            <Form.Control className="modal-input" type="file" name="csv" accept=".csv"
                                          onChange={(e) => setFormData((prev) => ({...prev, csv: e.target.files[0]}))}
                                          required placeholder="Choose your csv file"/>
                            <Row className="mb-3 justify-content-center">
                                <Col xs="auto">
                                    <button className="thbtn-add" type="submit">
                                        Upload
                                    </button>
                                </Col>
                                <Col xs="auto">
                                    <button className="thbtn-add" onClick={onClose}>
                                        Close
                                    </button>
                                </Col>
                            </Row>
                        </Form>
                    </Row>
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