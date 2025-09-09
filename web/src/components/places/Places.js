import React, {useEffect, useState} from 'react';

import {fetchAllPlaces} from "./api/api";
import {useDispatch, useSelector} from "react-redux";
import PlaceModal from "./modal/PlaceModal";

const Places = () => {
    const dispatch = useDispatch();
    const {places, loading, error} = useSelector(state => state.placeReducer);
    const [treeData, setTreeData] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formData, setFormData] = useState({
        id: "",
        title: "",
        genre: "",
        author: [],
        series: "",
        year: "",
        place: "",
        description: ""
    });
    const [selectedBook, setSelectedBook] = useState(null);

    const openModal = (type, book = null) => {
        setIsModalOpen(true);
    };

    const handleAddChild = (parentNode, childName) => {
        const addChild = (nodes) => {
            return nodes.map(node => {
                if (node.id === parentNode.id) {
                    const newChild = {
                        id: "",
                        name: childName,
                        level: node.level + 1,
                        description: '',
                        children: []
                    };
                    return {
                        ...node,
                        children: [...(node.children || []), newChild]
                    };
                } else if (node.children) {
                    return { ...node, children: addChild(node.children) };
                }
                return node;
            });
        };
        setTreeData(prev => addChild(prev));
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setFormData({id: "", name: "", parent: "", level: ""});
    };

    useEffect(() => {
        dispatch(fetchAllPlaces());
    }, [dispatch]);

    const handleAddPlace = () => {
        openModal("add");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Submitting form data:", formData);
        try {
           // await dispatch(addNewPlace(formData));
            closeModal();
        } catch (error) {
            console.error("Error submitting form:", error);
        }
    };

    if (loading) return (
        <div className='root'>
            <div className="main-container">
                <h3>Places component</h3>
                <p>Loading...</p>
            </div>
        </div>);
    if (error) return (
        <div className='root'>
            <div className="main-container">
                <h3>Places component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);


    const onNodeClick = (event, node) => {
        console.log("Edit node:", node)
    };

    return (
        <main className="main-container">
            <nav className="th-buttons-toolbar" aria-label="Places">
                <button className="th-main-button" onClick={handleAddPlace}>
                    Add place
                </button>
            </nav>

            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {places.map((e) => (
                        <tr key={e.id}>
                            <td>{e.id}</td>
                            <td>{e.name}</td>
                            <td>{e.description}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </section>
            <PlaceModal
                places={places}
                onCrossClick={onNodeClick}
                isOpen={isModalOpen}
                onClose={closeModal}
                onSubmit={handleSubmit}
                formData={formData}
                setFormData={setFormData}
                onAddChild={handleAddChild}
            />
        </main>
    );
}

export default Places;