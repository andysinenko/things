//import store from "../../../store/storeConfig";
//import {get} from "../../../api/axiosApi";
import {API_LOADING, FETCH_PLACE_SUCCESS, FETCH_PLACES_SUCCESS, FETCH_PLACES_FAILURE} from "./actionTypes";
import axios from "axios";


export const fetchPlace = ({placeId}) => {
    return dispatch => {
        dispatch(placesLoading());

        axios
            .get('http://localhost:8080/api/v1/places/' + placeId, {
                placeId,
                completed: false
            })
            .then(res => {
                dispatch(fetchPlacesSuccess(res.data));
            })
            .catch(err => {
                dispatch(fetchPlacesFailure(err.message));
            });
    };
};

const fetchPlacesSuccess = places => ({
    type: FETCH_PLACE_SUCCESS,
    payload: {
        ...places
    }
});

const placesLoading = () => ({
    type: API_LOADING
});

const fetchPlacesFailure = error => ({
    type: FETCH_PLACES_FAILURE,
    payload: {
        error
    }
});

export function fetchAllPlaces() {
    return dispatch => {
        dispatch(placesLoading());

        axios
            .get('http://localhost:8080/api/v1/places/', {
                completed: false
            })
            .then(res => {
                dispatch(fetchPlacesSuccess(res.data));
            })
            .catch(err => {
                dispatch(fetchPlacesFailure(err.message));
            });
    };
}

