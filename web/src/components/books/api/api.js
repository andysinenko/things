import {fetchBooksFailure, fetchBooksStart, fetchBooksSuccess} from "../reducer/BooksSlice";
import axios from "axios";


export const fetchBooks = async (dispatch) => {
    dispatch(fetchBooksStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/books");
        if (response.status === 200) {
            console.log("Success on fetching books: ", response.status);
            dispatch(fetchBooksSuccess(response.data));
        } else {
            console.log("Error on fetching books: ", response.status);
            dispatch(fetchBooksFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching books, catch section: ", error.message);
        dispatch(fetchBooksFailure(error.message));
    }
};

export const addNewBook = (dispatch, book) => {
    axios.post("http://localhost:8080/api/v1/books", book)
        .then(response => {
            if (response.status === 200) {
                console.log("Success on adding new book: ", response.status);
                dispatch(fetchBooksSuccess(response.data));
            } else {
                console.log("Error on adding new book: ", response.status);
                dispatch(fetchBooksFailure(`Error: ${response.status}`));
            }
        })
        .catch(error => {
            console.log("Error on adding new book, catch section: ", error.message);
            dispatch(fetchBooksFailure(error.message));
        });
};