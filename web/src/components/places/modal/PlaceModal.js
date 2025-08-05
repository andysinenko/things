import React from "react";
import {Form, Row, Modal, Button} from "react-bootstrap";
import "./PlaceModal.css";
import {TreeView} from "../TreeView";

const PlaceModal = ({
                        data,
                        onCrossClick,
                        isOpen,
                        onClose,
                        onSubmit,
                        formData,
                        setFormData,
                        modalType,
                        selectedPlace,
                        header,
                        onAddChild
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
        return (
            <Row className="justify-content-center">
                <Modal show={isOpen} onHide={handleChange}>
                    <Modal.Header closeButton>
                        <Modal.Title>{header}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <TreeView data={data} onCrossClick={onCrossClick}/>
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
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">{renderContent()}</div>
        </div>
    );
};

export default PlaceModal;