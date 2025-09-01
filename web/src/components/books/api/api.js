import {deleteBookFailure, deleteBookSuccess, fetchBooksFailure, fetchBooksStart, fetchBooksSuccess} from "../reducer/BooksSlice";
import axios from "axios";
import {fetchAuthorsFailure, fetchAuthorsStart, fetchAuthorsSuccess} from "../reducer/AuthorsSlice";
import {fetchSeriesFailure, fetchSeriesStart, fetchSeriesSuccess} from "../reducer/SeriesSlice";
import {fetchGenresSuccess, fetchGenresFailure, fetchGenresStart} from "../reducer/GenresSlice";
import store from "../../../store/storeConfig";


export const fetchBooks = (pageNumber, pageSize) => async (dispatch) => {
    dispatch(fetchBooksStart());
    try {
        const response = await axios.get(`http://localhost:8080/api/v1/books?pageNumber=${pageNumber}&pageSize=${pageSize}`);
        console.log("!!! response.data: " + response.data);
        dispatch(fetchBooksSuccess(response.data));
    } catch (err) {
        dispatch(fetchBooksFailure(err.message));
    }
};

export const addNewBook = (book) => async (dispatch) => {
    try {
        const response = await axios.post("http://localhost:8080/api/v1/books", book);
        if (response.status === 200 || response.status === 201) {
            console.log("Success on adding new book: ", response.status);
            const currentBooks = store.getState().booksReducer.books;
            dispatch(fetchBooksSuccess([...currentBooks, response.data]));
        } else {
            console.log("Error on adding new book: ", response.status);
            dispatch(fetchBooksFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on adding new book, catch section: ", error.message);
        dispatch(fetchBooksFailure(error.message));
    }
};


export const fetchAuthors = () => async (dispatch) => {
    dispatch(fetchAuthorsStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/authors");
        if (response.status === 200) {
            dispatch(fetchAuthorsSuccess(response.data));
        } else {
            dispatch(fetchAuthorsFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        if (error.config) {
            console.log("Request headers on error:", error.config.headers); // Debug: log headers on error
        }
        dispatch(fetchAuthorsFailure(error.message));
    }
};

export const fetchSeries = () => async (dispatch) => {
    dispatch(fetchSeriesStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/series");
        if (response.status === 200) {
            dispatch(fetchSeriesSuccess(response.data));
        } else {
            dispatch(fetchSeriesFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching series, catch section: ", error.message);
        if (error.config) {
            console.log("Request headers on error:", error.config.headers); // Debug: log headers on error
        }
        dispatch(fetchSeriesFailure(error.message));
    }
};

export const fetchGenres = () => async (dispatch) => {
    dispatch(fetchGenresStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/genres");
        if (response.status === 200) {
            console.log("Success on fetching genres: ", response.status);
            dispatch(fetchGenresSuccess(response.data));
        } else {
            console.log("Error on fetching genres: ", response.status);
            dispatch(fetchGenresFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching genres, catch section: ", error.message);
        if (error.config) {
            console.log("Request headers on error:", error.config.headers);
        }
        dispatch(fetchGenresFailure(error.message));
    }
};

export const deleteBook = (id) => async (dispatch) => {
    try {
        const resp = await axios.delete(`http://localhost:8080/api/v1/books/${id}`);
        if (resp.status === 200 || resp.status === 204) {
            console.log("Book deleted:", resp.status);
            // Instead of fetching all books, remove the book from the state
            dispatch(deleteBookSuccess(store.getState().booksReducer.books.filter(book => book.id !== id)));
        } else {
            dispatch(deleteBookFailure(`Delete failed: ${resp.status}`));
        }
    } catch (err) {
        console.error("Delete error:", err.message);
        dispatch(deleteBookFailure(err.message));
    }
};
