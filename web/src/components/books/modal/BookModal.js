import React from "react";
import {Form, Row, Col, Modal, Button} from "react-bootstrap";
import "./BookModal.css";

const BookModal = ({
                          isOpen,
                          onClose,
                          onSubmit,
                          formData,
                          setFormData,
                          modalType,
                          selectedBook,
                          header,
                          genres,
                          series,
                          authors = []
                      }) => {
    if (!isOpen) return null;

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setFormData((prev) => ({...prev, [name]: value}));
        } else {
            onClose();
        }
    };


    const renderContent = () => {
        switch (modalType) {
            case "add":
                return (
                    <Row className="justify-content-center">
                        <Modal show={isOpen} onHide={handleChange}>
                            <Modal.Header closeButton>
                                <Modal.Title>{header}</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <Row className="justify-content-center">
                                    <Form onSubmit={onSubmit} className="flex-column">
                                        <Form.Control className="modal-input" type="text" name="id" value={formData.id}
                                                      onChange={handleChange} placeholder="Enter ID of book"/>
                                        <Form.Control className="modal-input" type="text" name="title"
                                                      value={formData.title}
                                                      onChange={handleChange} placeholder="Enter title of book"/>
                                        <Form.Select size="4" aria-label="Genre name" value={formData.genre}
                                                     onChange={(e) => setFormData({...formData, genre: e.target.value})}
                                                     aria-placeholder="Select genre">
                                            <option value="" disabled hidden>Genre name</option>
                                            {genres.map((genre) => (
                                                <option key={genre.id} value={genre.id}>
                                                    {genre.name}
                                                </option>
                                            ))}
                                        </Form.Select>
                                        <Form.Select size="4"
                                                     multiple
                                                     aria-label="Authors"
                                                     value={formData.author.map(a => a.id)} // value — массив id
                                                     onChange={(e) => {
                                                         const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.value));
                                                         const selectedAuthors = authors.filter(author => selectedIds.includes(author.id));
                                                         setFormData({...formData, author: selectedAuthors});
                                                     }}
                                        >
                                            <option value="" disabled hidden>Authors</option>
                                            {authors.map((author) => (
                                                <option key={author.id} value={author.id}>
                                                    {author.name}
                                                </option>
                                            ))}
                                        </Form.Select>
                                        <Form.Select aria-label="Series" value={formData.series}
                                                     onChange={(e) => setFormData({
                                                         ...formData,
                                                         series: e.target.value
                                                     })}>
                                            {series.map((series) => (
                                                <option key={series.id} value={series.id}>
                                                    {series.name}
                                                </option>
                                            ))}
                                        </Form.Select>
                                        <Form.Control className="modal-input" type="text" name="year"
                                                      onChange={handleChange} placeholder="Enter year of release"
                                                      value={formData.year}/>
                                        <Form.Control className="modal-input" type="text" name="description"
                                                      onChange={handleChange} placeholder="Enter description"
                                                      value={formData.description}/>
                                    </Form>
                                </Row>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button variant="light" onClick={onClose}>
                                    Close
                                </Button>
                                <Button variant="light" onClick={onSubmit}>
                                    Save Changes
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    </Row>


                );
            case "delete":
                return (
                    <Row className="justify-content-center">
                        <Modal show={isOpen} onHide={handleChange}>
                            <Modal.Header closeButton>
                                <Modal.Title aria-setsize="2">Delete book</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <Row className="justify-content-center">
                                    <Form onSubmit={onSubmit}>
                                        <span>Are you sure you want to delete "{selectedBook?.title || "this book"}"?</span>
                                    </Form>
                                </Row>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button variant="light" onClick={onClose}>
                                    Close
                                </Button>
                                <Button variant="light" onClick={onSubmit}>
                                    Delete
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    </Row>
                );
            case "csv":
                return (
                    <Row className="justify-content-center">
                        <Modal show={isOpen} onHide={handleChange}>
                            <Modal.Header closeButton>
                                <Modal.Title>CSV upload....</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <Row className=" justify-content-center">
                                    <Form onSubmit={onSubmit}>
                                        <Form.Control className="modal-input" type="file" name="csv" accept=".csv"
                                                      onChange={(e) => setFormData((prev) => ({
                                                          ...prev,
                                                          csv: e.target.files[0]
                                                      }))}
                                                      required placeholder="Choose your csv file"/>
                                    </Form>
                                </Row>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button variant="light" onClick={onClose}>
                                    Close
                                </Button>
                                <Button variant="light" onClick={onSubmit}>
                                    Upload
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    </Row>);
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

export default BookModal;