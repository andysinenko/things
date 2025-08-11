import React from "react";
import "./ToolModal.css";
import {TreeView} from "../../places/treeview/TreeView";

const ToolModal = ({isOpen,
    onClose,
    onSubmit,
    formData,
    setFormData,
    selectedTool,
    selectedBrand,
    selectedPlace,
    places = [],
    brands = []}) => {

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
            <div className="th-modal-overlay">
                <div className="th-modal-content">
                    <div className="th-modal-header">
                        <h5>Add tool</h5>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={onSubmit}>
                            <input placeholder="id" className="th-main-input" name="id" />
                            <input placeholder="name of tool" className="th-main-input" name="name"/>
                            <input placeholder="brand" className="th-main-input" name="brand"/>
                            <input placeholder="" className="th-main-input" name="address"/>
                            <input placeholder="" className="th-main-input" name="year"/>
                            <input placeholder="" className="th-main-input" name="description"/>
                            <input placeholder="" className="th-main-input" name="place"/>
                        </form>
                    </div>
                    <div className="modal-footer">
                        <button className="th-main-button" onClick={onClose}>Close</button>
                        <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                    </div>
                </div>
            </div>
        );
    };

    return (
        <div className="th-modal-overlay">
            <div className="th-modal-content">{renderContent()}</div>
        </div>
    );
};

export default ToolModal;