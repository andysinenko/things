import axios from "axios";
import {fetchPlacesFailure, fetchPlacesStart, fetchPlacesSuccess} from "../reducer/PlaceSlice";
import {fetchBooksFailure, fetchBooksStart, fetchBooksSuccess} from "../../books/reducer/BooksSlice";



export const fetchBooks = () => async (dispatch) => {
    dispatch(fetchBooksStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/books");
        dispatch(fetchBooksSuccess(response.data));
    } catch (err) {
        dispatch(fetchBooksFailure(err.message));
    }
};


export const fetchAllPlaces =() => async (dispatch) => {
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
