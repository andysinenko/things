import React, {useEffect, useState} from 'react';

import {fetchAllPlaces} from "./api/api";
import {useDispatch, useSelector} from "react-redux";
import {sortPlacesByName} from "./reducer/PlaceSlice";

const INITIAL_SORT_MENU_TYPE = {sortType: 'id'};

const Places = () => {
    const dispatch = useDispatch();
    const {places, loading, error} = useSelector(state => state.placeReducer);
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);


    useEffect(() => {
        console.log("useEffect called");
        fetchAllPlaces(dispatch);
    }, [dispatch]);


    const onSortSelect = (event) => {
        console.log("onSortSeleced", event.target.value);
        switch (event.target.value) {
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
        <div className='Container'>

            <div className="tableContainer">
                <h3>Places component</h3>
                <table className="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Address</th>
                    </tr>
                    </thead>
                    <tbody>
                    {places.map((e) =>
                        <tr key={e.id}>
                            <td>{e.id}</td>
                            <td>{e.name}</td>
                            <td>{e.description}</td>
                            <td>{e.address}</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Places;