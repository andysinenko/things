import PlaceModal from "../../places/modal/PlaceModal";

const ToolModal = ({isOpen,
    onClose,
    onSubmit,
    formData,
    setFormData,
    modalType,
    selectedPlace,
    setSelectedPlace,
    setSelectedBrand,
    selectedTool,
    isTreeModalOpen,
    setIsTreeModalOpen,
    places=[],
    brands=[]}) => {

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

    if (!isOpen) {
        return null;
    }

    const closeTreeModal = () => {
        setIsTreeModalOpen(false);
    };

    const onNodeClick = (nodePlace) => {
        //setSelectedPlace(nodePlace.id);
        setSelectedPlace(nodePlace);
        const newformData = {...formData, place: nodePlace.id};
        setFormData(newformData);
        setIsTreeModalOpen(false);
    };

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setFormData((prev) => ({...prev, [name]: value}));
        } else {
            onClose();
        }
    };

    const getFullPlacePath = (place) => {
        if (!place) return '';
        const names = [];
        let current = place;
        while (current) {
            names.unshift(current.name);
            current = current.parent;
        }
        return names.join(' -> ');
    }

    const buildTree = data => {
        const map = new Map();
        const roots = [];

        data.forEach(item => {
            map.set(item.id, {...item, children: []});
        });

        map.forEach(item => {
            if (item.parent && map.has(item.parent.id)) {
                map.get(item.parent.id).children.push(item);
            } else {
                roots.push(item);
            }
        });

        return roots;
    };

    const placesTree = buildTree(places);

    const onPlacesOpenDialogBox = (e) => {
        e.preventDefault();
        setIsTreeModalOpen(true);
    };

    const handleTreeSubmit = (e) => {
        setIsTreeModalOpen(false);
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
                                    <input placeholder="id" className="th-main-input" name="id" value={formData.id} onChange={handleChange}  disabled={true}/>
                                    <input placeholder="name of tool" className="th-main-input" name="name" value={formData.name} onChange={handleChange}/>
                                    <select aria-label="Brands" value={formData.vendor} onChange={
                                        (e) => {
                                            const brandId = e.target.value;
                                            const newformData = {...formData, vendor: brandId};
                                            setFormData(newformData);
                                            //setSelectedBrand(brandId);
                                        }
                                    } aria-placeholder="Select brand">
                                        <option value="" defaultValue>Brand name</option>
                                        {brands.map((brand) => (
                                            <option key={brand.id} value={brand.id}>
                                                {brand.name}
                                            </option>
                                        ))}
                                    </select>

                                    <input placeholder="Serial number" type="text" className="th-main-input" name="serialNumber" value={formData.serialNumber} onChange={handleChange}/>
                                    <input placeholder="Year of purchase" type="text" className="th-main-input" name="dateOfPurchasing" value={formData.dateOfPurchasing} onChange={handleChange}/>

                                    {selectedPlace !== null && selectedPlace !== undefined ? (
                                        <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>Place selected: {getFullPlacePath(selectedPlace)} ✅</button>
                                    ) : (
                                        <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>What a place ❓</button>
                                    )}

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
                                <button type="button" className="th-main-button" onClick={onClose}>Close</button>
                                <button type="submit" className="th-main-button" onClick={onSubmit}>Save Changes</button>
                            </div>
                        </div>
                    </div>
                );
            case "edit":
                return (
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">
                            <div className="th-modal-header">
                                <h5>Edit tool</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <input placeholder="id" className="th-main-input" name="id" value={formData.id} onChange={handleChange}  disabled={true}/>
                                    <input placeholder="name of tool" className="th-main-input" name="name" value={formData.name} onChange={handleChange}/>
                                    <select aria-label="Brands" value={formData.vendor} onChange={
                                        (e) => {
                                            const brandId = e.target.value;
                                            const newformData = {...formData, vendor: brandId};
                                            setFormData(newformData);
                                            //setSelectedBrand(brandId);
                                        }
                                    } aria-placeholder="Select brand">
                                        <option value="" defaultValue>Brand name</option>
                                        {brands.map((brand) => (
                                            <option key={brand.id} value={brand.id}>
                                                {brand.name}
                                            </option>
                                        ))}
                                    </select>

                                    <input placeholder="Serial number" type="text" className="th-main-input" name="serialNumber" value={formData.serialNumber} onChange={handleChange}/>
                                    <input placeholder="Year of purchase" type="text" className="th-main-input" name="dateOfPurchasing" value={formData.dateOfPurchasing} onChange={handleChange}/>

                                    {selectedPlace !== null && selectedPlace !== undefined ? (
                                        <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>Place selected: {getFullPlacePath(selectedPlace)} ✅</button>
                                    ) : (
                                        <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>What a place ❓</button>
                                    )}

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
                                <button type="button" className="th-main-button" onClick={onClose}>Close</button>
                                <button type="submit" className="th-main-button" onClick={onSubmit}>Save Changes</button>
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
            default: return (<p>Unknown action</p>);
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
                data={placesTree}
                onCrossClick={onNodeClick}
                isOpen={isTreeModalOpen}
                onClose={closeTreeModal}
                onSubmit={handleTreeSubmit}
                onAddChild={onNodeClick}
            />
        </>
    );
};

export default ToolModal;