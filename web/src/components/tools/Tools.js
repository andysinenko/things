import './Tools.css'
import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useState} from "react";
import {addNewTool, deleteTool, fetchBrands, fetchTools, updateTool} from "./api/api";
import ToolModal from "./modal/ToolModal";
import {fetchAllPlaces} from "../places/api/api";
import {Paginator} from "../layout/pagination/Paginator";
import {sortByBrand, sortById, sortByName, sortByType} from "./reducer/ToolsSlice";


export const Tools = () => {
    const dispatch = useDispatch();

    const {tools, loading, error} = useSelector(state => state.toolsReducer);
    const {places, ploading, perror} = useSelector(state => state.placeReducer);
    const {brands, loading: brandsLoading, error: brandsError} = useSelector(state => state.brandsReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);

    const [selectedTool, setSelectedTool] = useState({
        id: null,
        name: "",
        toolType: "",
        serialNumber:"",
        vendor: null,
        place: null,
        dateOfPurchasing: "",
        description: "",
    });

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

    const openModal = (modalType) => {
        console.log("PLACES 1 Tools: ", places);
        setModalType(modalType);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setSelectedTool(null);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (modalType === "add") {
                console.log("ADDED Tool: ", selectedTool);
                dispatch(addNewTool(selectedTool));
            } else if (modalType === "delete") {
                dispatch(deleteTool(selectedTool.id));
            } else if (modalType === "edit") {
                console.log("EDITED TOOL: ", selectedTool);
                dispatch(updateTool(selectedTool.id, selectedTool));
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const handleAddTool = () => {
        setSelectedTool({
            id: null,
            name: "",
            toolType: "",
            serialNumber:"",
            vendor: null,
            place: null,
            dateOfPurchasing: "",
            description: "",
        });
        openModal("add");
    };

    const handleEditTool = (tool) => {
        setSelectedTool(tool);
        openModal("edit");
    }

    const handleDelTool = (tool) => {
        setSelectedTool(tool);
        openModal("delete", tool);
    }

    return (
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
                        <th scope="col" onClick={() => dispatch(sortById())}>ID &#x25be;&#x25b4;</th>
                        <th scope="col" onClick={() => dispatch(sortByName())}>Name  &#x25be;&#x25b4;</th>
                        <th scope="col" onClick={() => dispatch(sortByBrand())}>Brand  &#x25be;&#x25b4;</th>
                        <th scope="col" onClick={() => dispatch(sortByType())}>Tool type  &#x25be;&#x25b4;</th>
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
                            <td>{tool.dateOfPurchasing?.substring(0, 4)}</td>
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
                <Paginator />
            </section>

            <ToolModal
            isOpen={isModalOpen}
            onClose={closeModal}
            onSubmit={handleSubmit}
            modalType = {modalType}
            selectedTool={selectedTool}
            setSelectedTool={setSelectedTool}
            isTreeModalOpen={isTreeModalOpen}
            setIsTreeModalOpen={setIsTreeModalOpen}
            places={places}
            brands={brands} />
        </main>
    );

}