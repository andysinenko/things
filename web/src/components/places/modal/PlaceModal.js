import React from "react";
import "./PlaceModal.css";
import {TreeView} from "../treeview/TreeView";

const PlaceModal = ({
                        data,
                        onCrossClick,
                        isOpen,
                        onClose,
                        onSubmit,
                        formData,
                        setFormData,
                        onAddChild
                    }) => {
    if (!isOpen) return null;


    const renderContent = () => {
        return (
            <div className="th-modal-overlay">
                <div className="th-modal-content">
                    <div className="th-modal-header">
                        <h5>Add place</h5>
                        <button className="th-modal-close-btn" onClick={onClose}>×</button>
                    </div>

                    <div className="modal-body">
                        <TreeView data={data} onCrossClick={onCrossClick} onAddChild={onAddChild} />
                    </div>

                    <div className="modal-footer">
                        <button className="th-main-button" onClick={onClose}>Close</button>
                        <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="th-modal-overlay">
            <div className="th-modal-content">{renderContent()}</div>
        </div>
    );
};

export default PlaceModal;