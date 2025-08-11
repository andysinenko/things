import React from "react";
import "./ToolModal.css";
import {TreeView} from "../../places/treeview/TreeView";

const ToolModal = ({isOpen,
    onClose,
    onSubmit,
    formData,
    setFormData,
    modalType,
    selectedTool,
    selectedBrand,
    selectedPlace,
    places = [],
    brands = []}) => {

    const ToolTypes = [
        "DRILL",
        "PERFORATOR",
        "BELT_SANDER",
        "ANGLE_GRINDER",
        "WELDING_INVERTER",
        "JIG_SAW",
        "VIBRO_GRINDING_MACHINE",
        "CIRCULAR_SAW"
    ];

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
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">
                            <div className="th-modal-header">
                                <h5>Add tool</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <input placeholder="id" className="th-main-input" name="id" value={formData.id} onChange={handleChange}  />
                                    <input placeholder="name of tool" className="th-main-input" name="name" value={formData.name} onChange={handleChange}/>
                                    <select aria-label="Brands" value={formData.vendor} onChange={(e) => setFormData({ ...formData, vendor: e.target.value })} aria-placeholder="Select brand">
                                        <option value="" defaultValue>Brand name</option>
                                        {brands.map((brand) => (
                                            <option key={brand.id} value={brand.id}>
                                                {brand.name}
                                            </option>
                                        ))}
                                    </select>

                                    <input placeholder="Serial number" type="text" className="th-main-input" name="serialNumber" value={formData.serialNumber} onChange={handleChange}/>
                                    <input placeholder="Year of purchase" type="text" className="th-main-input" name="dateOfPurchasing" value={formData.dateOfPurchasing} onChange={handleChange}/>

                                    <select aria-label="Places" value={formData.place} onChange={(e) => setFormData({ ...formData, place: e.target.value })} aria-placeholder="Select place">
                                        <option value="" defaultValue>Places</option>
                                        {places.map((place) => (
                                            <option key={place.id} value={place.id}>
                                                {place.id} - {place.name} - {place.description}
                                            </option>
                                        ))}
                                    </select>

                                    <select id="toolType" value={formData.toolType} onChange={e => setFormData({...formData, toolType: e.target.value})} aria-placeholder="Select tool type">
                                        <option value="" defaultValue>Tool type</option>
                                        {ToolTypes.map((tool) => (
                                            <option key={tool} value={tool}>
                                                {tool}
                                            </option>
                                        ))}
                                    </select>

                                    <input placeholder="Description" className="th-main-input" name="description" value={formData.description} onChange={handleChange}/>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="th-main-button" onClick={onClose}>Close</button>
                                <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                            </div>
                        </div>
                    </div>
                );
            case "delete" :
                return (
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">
                            <div className="th-modal-header">
                                <h5>Delete tool</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <span>Are you sure you want to delete "{selectedTool?.title || "this tool"}"?</span>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="th-main-button" onClick={onClose}>Close</button>
                                <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                            </div>
                        </div>
                    </div>
                );
        }
    };

    return (
        <div className="th-modal-overlay">
            <div className="th-modal-content">{renderContent()}</div>
        </div>
    );
};

export default ToolModal;