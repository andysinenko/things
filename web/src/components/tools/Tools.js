import { useDispatch, useSelector } from "react-redux";
import React, { useEffect, useState } from "react";
import { fetchTools, addNewTool, updateTool, deleteTool, sortById, sortByName, sortByBrand, sortByType } from "./reducer/ToolsSlice";
import { fetchBrands } from "./reducer/BrandsSlice";
import { fetchAllPlaces } from "../places/reducer/PlaceSlice";
import ToolModal from "./modal/ToolModal";
import { Paginator } from "../layout/pagination/Paginator";


export const Tools = () => {
    const dispatch = useDispatch();

    const {tools, loading, error} = useSelector(state => state.toolsReducer);
    const total      = useSelector(state => state.toolsReducer.total);
    const pageNumber = useSelector(state => state.toolsReducer.pageNumber);
    const pageSize   = 15;

    const {places} = useSelector(state => state.placeReducer);
    const {brands} = useSelector(state => state.brandsReducer);

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
        dispatch(fetchTools({ pageNumber: 0, pageSize }));
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
                dispatch(addNewTool(selectedTool));
            } else if (modalType === "delete") {
                dispatch(deleteTool(selectedTool.id));
            } else if (modalType === "edit") {
                dispatch(updateTool({ id: selectedTool.id, tool: selectedTool }));
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

    const onChangePage = (pageNumber, pageSize) => {
        dispatch(fetchTools({ pageNumber: 0, pageSize }));
    };

    return (
        <main className="main-container">
            {/* ── Toolbar / Operations with tools ── */}
            <nav className="th-buttons-toolbar" aria-label="Tools">
                <button type="button" className="thbtn-add" onClick={handleAddTool}>
                    + Add tool
                </button>
            </nav>

            {/* ── Table ── */}
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
                        <th style={{ textAlign: "center" }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {Array.isArray(tools) && tools.length !== 0 ? tools
                    .filter(tool => typeof tool === 'object' && tool !== null && 'id' in tool)
                    .map((tool) =>
                        <tr key={tool.id}>
                            <td style={{ color: "#9ca3af" }}>{tool.id}</td>
                            <td style={{ fontWeight: 500 }}>{tool.name}</td>
                            <td style={{ color: "#6b7280" }}>{tool.vendor?.name}</td>
                            <td>{tool.toolType}</td>
                            <td>{tool.serialNumber}</td>
                            <td>{tool.dateOfPurchasing?.substring(0, 4)}</td>
                            <td>{tool.place?.description}</td>
                            <td>{tool.description}</td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        onClick={() => handleEditTool(tool)}
                                        aria-label="Edit tool"
                                    >
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        onClick={() => handleDelTool(tool)}
                                        aria-label="Delete tool"
                                    >
                                        ✕
                                    </button>
                                </div>
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
                <Paginator
                    pageNumber={pageNumber}
                    totalPages={total}
                    pageSize={pageSize}
                    onChangePage={onChangePage}
                />
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