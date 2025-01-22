import React, {useEffect} from 'react';
import AppHeader from "../app-header";
import {fetchAllPlaces, fetchPlace} from "./reducer/placeActions";
import {useDispatch, useSelector} from "react-redux";


const Places = () => {
    const dispatch = useDispatch();
    const places = useSelector(state => state.placeReducer.places);

    useEffect(() => {
        dispatch(fetchAllPlaces());
    }, [dispatch]);

    return (
        <div className='Container'>
            <AppHeader/>
            <div className="main-container">
                <h1>Places component</h1>
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