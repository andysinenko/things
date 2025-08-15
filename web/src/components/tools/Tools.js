import './Tools.css'
import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useState} from "react";
import {addNewTool, deleteTool, fetchBrands, fetchTools} from "./api/api";
import ToolModal from "./modal/ToolModal";
import {fetchAllPlaces} from "../places/api/api";


export const Tools = () => {
    const dispatch = useDispatch();

    const {tools, loading, error} = useSelector(state => state.toolsReducer);
    const {places, loading: placesLoading, error: placesError} = useSelector(state => state.placeReducer);
    const {brands, loading: brandsLoading, error: brandsError} = useSelector(state => state.brandsReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);
    const [formData, setFormData] = useState({
        id: "",
        name: "",
        toolType: "",
        serialNumber:"",
        vendor: null, //vendor id
        place: null,  //place id
        dateOfPurchasing: "",
        description: "",
    });

    const [selectedTool, setSelectedTool] = useState(null);
    const [selectedPlace, setSelectedPlace] = useState(null);
    const [selectedBrand, setSelectedBrand] = useState(null);

    const [isTreeModalOpen, setIsTreeModalOpen] = useState(false);

    useEffect(() => {
        dispatch(fetchTools());
        dispatch(fetchBrands());
        dispatch(fetchAllPlaces());
    }, [dispatch]);

    if (loading) return (
        <div className='root'>
            <div className="main-container">
                <h3>Tools component</h3>
                <p>Loading...</p>
            </div>
        </div>);
    if (error) return (
        <div className='root'>
            <div className="main-container">
                <h3>Tools component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);

    const openModal = (modalType, tool = null) => {
        setModalType(modalType);
        setSelectedTool(tool);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setFormData({
            id: "", name: "", toolType: "", serialNumber:"", vendor: "", place: "", dateOfPurchasing: "", description: "",
        });
        setSelectedTool(null);
        setSelectedBrand(null);
        setSelectedPlace(null);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const vendorObject = brands.find(b => b.id === Number(selectedBrand)) || null;
        try {
            if (modalType === "add") {
                const newTool = {
                    id: formData.id,
                    name: formData.name,
                    toolType: formData.toolType,
                    serialNumber: formData.serialNumber,
                    vendor: formData.vendor,
                    place: formData.place,
                    dateOfPurchasing: `${formData.dateOfPurchasing}-01-01`,
                    description: formData.description,
                };
                dispatch(addNewTool(newTool));
            } else if (modalType === "delete") {
                dispatch(deleteTool(selectedTool.id));
            } else if (modalType === "edit") {
                //dispatch(addNewTool(newTool));
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const handleAddTool = () => {
        openModal("add");
    };

    const handleEditTool = (tool) => {
        const newSelectedTool = {
            id:             Number(tool.id),
            name:           tool.name,
            toolType:       tool.toolType,
            serialNumber:   tool.serialNumber,
            vendor:         tool.vendor.id,
            place:          tool.place,
            dateOfPurchasing: tool.dateOfPurchasing.substring(0, 4),
            description:    tool.description,
        };
        setFormData(newSelectedTool);
        setSelectedPlace(tool.place);
        openModal("edit", tool);
    }

    const handleDelTool = (tool) => {
        openModal("delete", tool);
    }

    return (
        <>
        <main className="main-container">
            <nav className="th-buttons-toolbar" aria-label="Tools">
                <button className="th-main-button" variant="light" size="sm" onClick={handleAddTool}>
                    Add tool
                </button>
            </nav>
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">Name</th>
                        <th scope="col">Brand</th>
                        <th scope="col">Tool type</th>
                        <th scope="col">Serial number</th>
                        <th scope="col">Purchasing date</th>
                        <th scope="col">Place</th>
                        <th scope="col">Description</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    {Array.isArray(tools) && tools.length !== 0 ? tools
                    .filter(tool => typeof tool === 'object' && tool !== null && 'id' in tool)
                    .map((tool) =>
                        <tr key={tool.id}>
                            <td>{tool.id}</td>
                            <td>{tool.name}</td>
                            <td>{tool.vendor?.name}</td>
                            <td>{tool.toolType}</td>
                            <td>{tool.serialNumber}</td>
                            <td>{tool.dateOfPurchasing}</td>
                            <td>{tool.place?.description}</td>
                            <td>{tool.description}</td>
                            <td>
                                <button className="table-action-btn edit-btn" title="Редактировать" onClick={() => handleEditTool(tool)}>✏️</button>
                            </td>
                            <td>
                                <button className="table-action-btn delete-btn" title="Удалить" onClick={() => handleDelTool(tool)}>🗑️</button>
                            </td>
                        </tr>
                        ): (
                        <tr>
                            <td colSpan="10" style={{textAlign: "center"}}>
                                <h5>Tools list is empty</h5>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>
        </main>
    <ToolModal
        isOpen={isModalOpen}
        onClose={closeModal}
        onSubmit={handleSubmit}
        formData={formData}
        setFormData={setFormData}
        modalType = {modalType}
        selectedTool={selectedTool}
        selectedPlace={selectedPlace}
        setSelectedPlace={setSelectedPlace}
        setSelectedBrand={setSelectedBrand}
        isTreeModalOpen={isTreeModalOpen}
        setIsTreeModalOpen={setIsTreeModalOpen}
        places={places}
        brands={brands} />
    </>
    );

}