import axios from "axios";
import {fetchPdfBooksFailure, fetchPdfBooksStart, fetchPdfBooksSuccess} from "../reducer/PdfBooksSlice";
import {fetchCategoriesError, fetchCategoriesSuccess} from "../reducer/CategoriesSlice";
import {fetchPdfAuthorsFailure, fetchPdfAuthorsStart, fetchPdfAuthorsSuccess} from "../reducer/PdfAuthorsSlice";
import {fetchSeriesStart} from "../../books/reducer/SeriesSlice";
import store from "../../../store/storeConfig";
import {fetchBooksFailure, fetchBooksSuccess} from "../../books/reducer/BooksSlice";


export const fetchPdfBooks= (pageNumber, pageSize) => async (dispatch) => {
    dispatch(fetchPdfBooksStart());
    try {
        const response = await axios.get(`http://localhost:8080/api/v1/pdfbooks?pageNumber=${pageNumber}&pageSize=${pageSize}`);
        console.log("pdfbooks response.data: " + response.data);
        dispatch(fetchPdfBooksSuccess(response.data));
    } catch (err) {
        dispatch(fetchPdfBooksFailure(err.message));
    }
};

export const fetchCategories = () => async (dispatch) => {
    dispatch(fetchSeriesStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/pdfbooks/categories");
        if (response.status === 200) {
            dispatch(fetchCategoriesSuccess(response.data));
        } else {
            dispatch(fetchCategoriesError(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching categories, catch section: ", error.message);
        if (error.config) {
            console.log("Request headers on error:", error.config.headers); // Debug: log headers on error
        }
        dispatch(fetchCategoriesError(error.message));
    }
};

export const fetchPdfAuthors = () => async (dispatch) => {
    dispatch(fetchPdfAuthorsStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/pdfbooks/pdfautors");
        if (response.status === 200) {
            console.log("Success on fetching genres: ", response.status);
            dispatch(fetchPdfAuthorsSuccess(response.data));
        } else {
            console.log("Error on fetching pdf authors: ", response.status);
            dispatch(fetchPdfAuthorsFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching pdf authors, catch section: ", error.message);
        if (error.config) {
            console.log("Request headers on error:", error.config.headers);
        }
        dispatch(fetchPdfAuthorsFailure(error.message));
    }
};

export const uploadPdfBook = (formData) => async (dispatch) => {
    try {
        const response = await axios.post("http://localhost:8080/api/v1/pdfbooks/upload", formData);
        if (response.status === 200 || response.status === 201) {
            console.log("Success on adding new pdfbook: ", response.status);
            const currentBooks = store.getState().pdfBooksReducer.books;
            dispatch(fetchBooksSuccess([...currentBooks, response.data]));
        } else {
            console.log("Error on adding new pdfbook: ", response.status);
            dispatch(fetchBooksFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on adding new pdfbook, catch section: ", error.message);
        dispatch(fetchBooksFailure(error.message));
    }
};

