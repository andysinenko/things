import PlaceModal from "../../places/modal/PlaceModal";
import React from "react";

const ToolModal = ({
                       isOpen,
                       onClose,
                       onSubmit,
                       modalType,
                       selectedTool,
                       setSelectedTool,
                       isTreeModalOpen,
                       setIsTreeModalOpen,
                       places,
                       brands
                   }) => {

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

    const closeTreeModal = () => {
        setIsTreeModalOpen(false);
    };

    const onNodeSelect = (nodePlace) => {
        setSelectedTool({...selectedTool, place: nodePlace});
        setIsTreeModalOpen(false);
    };

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setSelectedTool((prev) => ({...prev, [name]: value}));
        } else {
            onClose();
        }
    };


    const getFullPlacePath = (place) => {
        if (!place) return '';
        const currentPlaces = [];
        let current = place;
        while (current) {

            console.log(">>> brands: ", brands);
            console.log(">>> selectedTool: ", selectedTool);

            currentPlaces.unshift(current.name);
            current = current.parent;
        }
        return currentPlaces.join(' -> ');
    }

    const onPlacesOpenDialogBox = (e) => {
        e.preventDefault();
        setIsTreeModalOpen(true);
    };

    const handleTreeSubmit = (e) => {
        setIsTreeModalOpen(false);
    };

    console.log("PLACES 4 ToolModal: ", places);

    if (!isOpen) {
        return null;
    }
    const renderContent = () => {
        switch (modalType) {
            case "add":
                return (
                    <>
                        <div className="th-modal-header">
                            <span className="th-modal-title">Add tool</span>
                            <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">
                                ✖️
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="modal-field-row">
                                <div className="modal-field">
                                    <label>Name</label>
                                    <input placeholder="name of tool" className="modal-input" name="name"
                                           value={selectedTool.name ?? ""} onChange={handleChange} maxLength="512"/>
                                </div>
                                <div className="modal-field">
                                    <label>Brand</label>
                                    <select aria-label="Brands" value={selectedTool.vendor} onChange={
                                        (e) => {
                                            const brandId = Number(e.target.value);
                                            const brand = brands.find(brand => brand.id === brandId);
                                            console.log(">>> brandId: ", brandId);
                                            console.log(">>> brand: ", brand);
                                            console.log(">>> brands: ", brands);
                                            console.log(">>> selectedTool: ", selectedTool);

                                            const newSelectedTool = {...selectedTool, vendor: brand};
                                            setSelectedTool(newSelectedTool);
                                        }
                                    }>
                                        <option value="" defaultValue>Brand name</option>
                                        {brands.map((brand) => (
                                            <option key={brand.id} value={brand.id}>
                                                {brand.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="modal-field-row">
                                <div className="modal-field">
                                    <label>Serial no</label>
                                    <input placeholder="Serial number" type="text" className="modal-input"
                                           name="serialNumber" value={selectedTool.serialNumber ?? ""}
                                           onChange={handleChange} maxLength="512"/>
                                </div>
                                <div className="modal-field">
                                    <label>Year</label>
                                    <input placeholder="Year of purchase" type="date" className="modal-input"
                                           name="dateOfPurchasing" value={selectedTool.dateOfPurchasing ?? ""}
                                           onChange={handleChange} maxLength="512"/>
                                </div>
                            </div>

                            <div className="modal-field">
                                <label>Place</label>
                                {selectedTool.place !== null && selectedTool.place !== undefined ? (
                                    <button type="button" className="th-main-button"
                                            onClick={onPlacesOpenDialogBox}>Place
                                        selected: {getFullPlacePath(selectedTool.place)} ✅</button>
                                ) : (
                                    <button type="button" className="th-main-button"
                                            onClick={onPlacesOpenDialogBox}>What a place ❓</button>
                                )}
                            </div>
                            <div className="modal-field">
                                <label>Type</label>
                                <select id="toolType" value={selectedTool.toolType} onChange={e =>
                                    setSelectedTool({...selectedTool, toolType: e.target.value})}>
                                    <option value="" defaultValue>Tool type</option>
                                    {ToolTypes.map((tool) => (
                                        <option key={tool} value={tool}>
                                            {tool}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="modal-field">
                                <label>Description</label>
                                <input placeholder="Description" className="th-main-input" name="description"
                                       value={selectedTool.description ?? ""} onChange={handleChange}
                                       maxLength="512"/>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="th-main-button" onClick={onClose}>Close</button>
                            <button type="submit" className="th-main-button" onClick={onSubmit}>Save Changes</button>
                        </div>
                    </>
                );
            case "edit":
                return (
                    <>
                        <div className="th-modal-header">
                            <span className="th-modal-title">
                                Edit tool
                            </span>
                            <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">
                                ✖️
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="modal-field-row">
                                <div className="modal-field">
                                    <label>Name</label>
                                    <input placeholder="name of tool" className="modal-input" name="name"
                                           value={selectedTool.name ?? ""} onChange={handleChange} maxLength="512"/>
                                </div>
                                <div className="modal-field">
                                    <label>Brands</label>
                                    <select
                                        aria-label="Brands"
                                        value={selectedTool.vendor?.id || ""}
                                        onChange={(e) => {
                                            const brandId = Number(e.target.value);
                                            const selectedBrand = brands.find(brand => brand.id === brandId);
                                            const newSelectedTool = {...selectedTool, vendor: selectedBrand || null};
                                            setSelectedTool(newSelectedTool);
                                        }}>
                                        <option value="" defaultValue>Brand name</option>
                                        {brands.map((brand) => (
                                            <option key={brand.id} value={brand.id}>
                                                {brand.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="modal-field-row">
                                <div className="modal-field">
                                    <label>Serial No.</label>
                                    <input placeholder="Serial number" type="text" className="modal-input"
                                           name="serialNumber" value={selectedTool.serialNumber ?? ""}
                                           onChange={handleChange} maxLength="512"/>
                                </div>
                                <div className="modal-field">
                                    <label>Year</label>
                                    <input placeholder="Year of purchase" type="date" className="modal-input"
                                           name="dateOfPurchasing" value={selectedTool.dateOfPurchasing ?? ""}
                                           onChange={handleChange} maxLength="512"/>
                                </div>
                            </div>
                            <div className="modal-field">
                                <label>Place</label>
                                {selectedTool.place !== null && selectedTool.place !== undefined ? (
                                    <button type="button" className="th-main-button"
                                            onClick={onPlacesOpenDialogBox}>Place
                                        selected: {getFullPlacePath(selectedTool.place)} ✅</button>
                                ) : (
                                    <button type="button" className="th-main-button"
                                            onClick={onPlacesOpenDialogBox}>What a place ❓</button>
                                )}
                            </div>
                            <div className="modal-field">
                                <label>Type</label>
                                <select id="toolType" value={selectedTool.toolType} onChange={e =>
                                    setSelectedTool({...selectedTool, toolType: e.target.value})}>
                                    <option value="" defaultValue>Tool type</option>
                                    {ToolTypes.map((tool) => (
                                        <option key={tool} value={tool}>
                                            {tool}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="modal-field">
                                <label>Description</label>
                                <input placeholder="Description" className="modal-input" name="description"
                                       value={selectedTool.description ?? ""} onChange={handleChange}
                                       maxLength="512"/>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="th-main-button" onClick={onClose}>Close</button>
                            <button type="submit" className="th-main-button" onClick={onSubmit}>Save</button>
                        </div>
                    </>
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
                                <button className="th-main-button" onClick={onSubmit}>Save</button>
                            </div>
                        </div>
                    </div>
                );
            default:
                return (<p>Unknown action</p>);
        }
    };

    return (
        <>
            {isOpen && (
                <div className="th-modal-overlay">
                    <div className="th-modal-content">
                        {renderContent()}
                    </div>
                </div>
            )}

            <PlaceModal
                places={places}
                onCrossClick={onNodeSelect}
                isOpen={isTreeModalOpen}
                onClose={closeTreeModal}
                onSubmit={handleTreeSubmit}
                onAddChild={onNodeSelect}
            />
        </>
    );
};

export default ToolModal;