import axios from "axios";
import {fetchPlacesFailure, fetchPlacesStart, fetchPlacesSuccess} from "../reducer/PlaceSlice";


export const fetchAllPlaces = async (dispatch) => {
    dispatch(fetchPlacesStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/places");
        if (response.status === 200) {
            console.log("Success on fetching places: ", response.status);
            dispatch(fetchPlacesSuccess(response.data));
        } else {
            console.log("Error on fetching places: ", response.status);
            dispatch(fetchPlacesFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching places, catch section: ", error.message);
        dispatch(fetchPlacesFailure(error.message));
    }
};
