import React from 'react';
import AppHeader from "../app-header";
import {fetchAllPlaces, fetchPlace} from "./reducer/placeAction";
import store from "../../store/storeConfig";
import {connect} from "react-redux";
import placeReducer from "./reducer/placeReducer";


class PlacesClass extends React.Component {
    constructor(props) {
        super(props);

    }

    componentDidMount() {
        //let places = this.props.fetchAllPlaces();
        let places = this.props.fetchPlace(1);
        console.log("places", places)
    }

    render() {
        //const {places} = this.state;
        console.log("store.places", this.props.places.placeReducer.places.places)
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
                        {this.props.places.placeReducer.places.places?.map((e) =>
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
    };
}

function mapStateToProps(state) {
    return {
        places: placeReducer(state, "places"),
    };
}

const Places = connect(mapStateToProps, {
    fetchAllPlaces,
    fetchPlace
})(PlacesClass);

export default Places;