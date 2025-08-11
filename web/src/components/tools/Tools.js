import './Tools.css'
import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useState} from "react";
import {addNewTool, deleteTool, fetchBrands, fetchTools} from "./api/api";
import ToolModal from "./modal/ToolModal";
import {fetchAllPlaces} from "../places/api/api";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";


export const Tools = () => {
    const dispatch = useDispatch();
    const sortMenu = [
        {id: 1, value: 'id', innerText: 'id', key: 'id'},
        {id: 2, value: 'title', innerText: 'Title', key: 'id'},
        {id: 3, value: 'reverse', innerText: 'Reverse', key: 'id'},
        {id: 4, value: 'genre', innerText: 'Genre', key: 'id'}];
    const {tools, loading, error} = useSelector(state => state.toolsReducer);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);
    const [formData, setFormData] = useState({
        id: "",
        name: "",
        toolType: "",
        serialNumber:"",
        vendor: {},
        place: {},
        dateOfPurchasing: "",
        description: "",
    });

    const [selectedTool, setSelectedTool] = useState(null);
    const [selectedPlace, setSelectedPlace] = useState(null);
    const [selectedBrand, setSelectedBrand] = useState(null);
    const {places, placesLoading: placesLoading, placesError: placesError} = useSelector(state => state.placeReducer);
    const {brands, brandsLoading: brandsLoading, brandsError: brandsError} = useSelector(state => state.brandsReducer);

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
            id: "", name: "", toolType: "", serialNumber:"", vendor: {}, place: {}, dateOfPurchasing: "", description: "",
        });
        setSelectedTool(null);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log( modalType + " " + formData.name);
        try {
            if (modalType === "add") {
                const vendorObj = brands.find(v => v.id === Number(formData.vendor)) || null;
                const placeObj = places.find(p => p.id === Number(formData.place)) || null;

                const newTool =
                    {
                        id: formData.id,
                        name: formData.name,
                        toolType: formData.toolType,
                        serialNumber: formData.serialNumber,
                        vendor: vendorObj ? { id: vendorObj.id, name: vendorObj.name } : null,
                        place: placeObj ? { id: placeObj.id, name: placeObj.name, description: placeObj.description } : null,
                        dateOfPurchasing: `${formData.dateOfPurchasing}-01-01`,
                        description: formData.description
                    };
                dispatch(addNewTool(newTool));
            } else if (modalType === "delete") {
                console.log("Deleting book:", selectedTool);
                dispatch(deleteTool(selectedTool.id));
            }
            closeModal();

        } catch (error) {
            console.error("Error:", error);
        }
    };

    const handleAddTool = () => {
        openModal("add");
    };

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
                        <th scope="col">ID</th>
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
                            <td>{tool.place?.description || "—"}</td>
                            <td>{tool.description}</td>
                            <td>
                                <button type="button" className="btn btn btn-warning"></button>
                            </td>
                            <td>
                                <button onClick={() => handleDelTool(tool)}
                                        style={{cursor: 'pointer', border: 'none', background: 'none'}}>
                                    <FontAwesomeIcon icon={faTrash} size="lg" color="red"/>
                                </button>
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
        selectedBrand={selectedBrand}
        selectedPlace={selectedPlace}
        places={places}
        brands={brands}
    />
    </>
    );

}