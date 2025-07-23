import React, {useEffect, useState} from 'react';

import {fetchAllPlaces} from "./api/api";
import {useDispatch, useSelector} from "react-redux";
import {sortPlacesById, sortPlacesByName} from "./reducer/PlaceSlice";
import {Button, Container, Row} from "react-bootstrap";
import ThSelect from "../layout/select/th-select";

const INITIAL_SORT_MENU_TYPE = 'id';

const Places = () => {
    const dispatch = useDispatch();
    const {places, loading, error} = useSelector(state => state.placeReducer);
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);


    useEffect(() => {
        console.log("useEffect called");
        dispatch(fetchAllPlaces());
    }, [dispatch]);

    const handleAddPlace = () => {

    }

    const onSortSelect = (event) => {
        console.log("onSortSeleced", event.target.value);
        switch (event.target.value) {
            case "id":
                dispatch(sortPlacesById());
                break;
            case "name":
                dispatch(sortPlacesByName());
                break;
            default:
                break;
        }
        ;
    };

    if (loading) return (
        <div className='Container'>

            <div className="main-container">
                <h3>Places component</h3>
                <p>Loading...</p>
            </div>
        </div>);
    if (error) return (
        <div className='Container'>

            <div className="main-container">
                <h3>Places component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);

    return (
        <div className="content-container">
            <div className='Container'>
                <div className="main-container">
                    <div className="d-flex align-items-center gap-3 mb-3">
                        <h4>My places</h4>
                        <select onChange={onSortSelect} value={sortType.sortType} style={{marginLeft: '8px'}}>
                            <option>Sort by...</option>
                            <option value="id">Sort by ID</option>
                            <option value="name">Sort by Name</option>
                        </select>
                        <Button variant="secondary" size="sm" onClick={handleAddPlace}>
                            Add place
                        </Button>
                    </div>

                    <div className="tableContainer">
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
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Places;