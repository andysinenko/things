//import store from "../../../store/storeConfig";
//import {get} from "../../../api/axiosApi";
import {API_LOADING, FETCH_PLACE_SUCCESS, FETCH_PLACES_SUCCESS, FETCH_PLACES_FAILURE} from "./actionTypes";
import axios from "axios";


/*export const fetchPlace2 = (placeId) => {
    return async (dispatch) => {
        try {
            dispatch({ type: 'API_LOADING' });
            const response = await fetch('http://localhost:8080/api/v1/places/' + placeId);
            const places = await response.json();
            dispatch({ type: 'FETCH_PLACE_SUCCESS', payload: places });
        } catch (error) {
            dispatch({ type: 'FETCH_PLACES_FAILURE', payload: error.message });
        }
    };
};*/

export const fetchPlace = (placeId) => {
    return (dispatch) => {
        dispatch(placesLoading());
        return axios
            .get('http://localhost:8080/api/v1/places/' + placeId)
            .then(data => {
                const places = data.data;
                console.log("from fetchPlace: ", places);
                dispatch( fetchPlacesSuccess(places) );
            }).catch(error => {
                dispatch( fetchPlacesFailure(error.message) );
            });
    };
};

export const fetchAllPlaces = () => {
    return async (dispatch) => {
        dispatch(placesLoading());
        return axios
            .get('http://localhost:8080/api/v1/places')
            .then(data => {
                const places = data.data;
                console.log("from fetchAllPlaces: ", places);
                dispatch( fetchPlacesSuccess(places) );
            }).catch(error => {
                dispatch( fetchPlacesFailure(error.message) );
            });
    };
};

const fetchPlacesSuccess = places => { return {
    type: FETCH_PLACE_SUCCESS,
    //payload: [...places]
    payload: places
}};

const placesLoading = (state) => ({
    type: API_LOADING,
    payload: state
});

const fetchPlacesFailure = error => ({
    type: FETCH_PLACES_FAILURE,
    payload: {
        error
    }
});

