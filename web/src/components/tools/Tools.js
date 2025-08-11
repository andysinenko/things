import './Tools.css'
import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useState} from "react";
import {addNewTool, fetchBrands, fetchTools} from "./api/api";
import ToolModal from "./modal/ToolModal";
import {fetchAllPlaces} from "../places/api/api";

export const Tools = () => {
    const dispatch = useDispatch();
    const {tools, loading, error} = useSelector(state => state.toolsReducer);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);
    const [formData, setFormData] = useState({
        id: "",
        name: "",
        brand: "",
        address: "",
        year: "",
        description: "",
        place: {},
    });

    const [selectedTool, setSelectedTool] = useState(null);
    const [selectedPlace, setSelectedPlace] = useState(null);
    const [selectedBrand, setSelectedBrand] = useState(null);
    const {places, loading: placesLoading, error: placesError} = useSelector(state => state.placeReducer);
    const {brands, loading: brandsLoading, error: brandsError} = useSelector(state => state.brandsReducer);

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

    const openModal = (tool = null) => {
        setSelectedTool(null);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setFormData({id: "", title: "", genre: "", author: [], series: "", year: "", place: "", description: ""});
        setSelectedTool(null);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const newTool =
                {
                    id:         formData.id,
                    name:       formData.name,
                    brand:      formData.brand,
                    address:    formData.address,
                    year:       formData.year,
                    description:formData.description,
                    place:      formData.place ? formData.place.id : null
                };
            dispatch(addNewTool(newTool));
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };


    const handleAddTool = () => {
        openModal();
    };

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
                        <th scope="col">Year</th>
                        <th scope="col">Address</th>
                        <th scope="col">Description</th>
                        <th scope="col">Place</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    {tools.map((e) =>
                        <tr key={e.id}>
                            <td>{e.id}</td>
                            <td>{e.name}</td>
                            <td>{e.brand}</td>
                            <td>{e.year}</td>
                            <td>{e.address}</td>
                            <td>{e.description}</td>
                            <td>{e.place.description}</td>
                            <td>
                                <button type="button" className="btn btn btn-warning"></button>
                            </td>
                            <td><a href="bbb" onClick={this.test}>
                                <i className="fa fa-trash" aria-hidden="true"></i></a>
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
        selectedTool={selectedTool}
        selectedBrand={selectedBrand}
        selectedPlace={selectedPlace}
        places={places}
        brands={brands}
    />
    </>
    );

}