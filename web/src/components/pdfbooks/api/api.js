import axios from "axios";
import {
    addPdfBookSuccess,
    deletePdfBookFailure,
    deletePdfBookSuccess,
    fetchPdfBooksFailure,
    fetchPdfBooksStart,
    fetchPdfBooksSuccess,
    updatePdfBookSuccess
} from "../reducer/PdfBooksSlice";
import {fetchCategoriesError, fetchCategoriesStart, fetchCategoriesSuccess} from "../reducer/CategoriesSlice";
import {fetchPdfAuthorsFailure, fetchPdfAuthorsStart, fetchPdfAuthorsSuccess} from "../reducer/PdfAuthorsSlice";


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
    dispatch(fetchCategoriesStart());
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
        const response = await axios.get("http://localhost:8080/api/v1/pdfbooks/pdfauthors");
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
            dispatch(addPdfBookSuccess(response.data));
        } else {
            console.log("Error on adding new pdfbook: ", response.status);
            dispatch(fetchPdfBooksFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on adding new pdfbook, catch section: ", error.message);
        dispatch(fetchPdfBooksFailure(error.message));
    }
};

export const deletePdfBook = (id) => async (dispatch) => {
    try {
        const resp = await axios.delete(`http://localhost:8080/api/v1/pdfbooks/${id}`);
        if (resp.status === 200 || resp.status === 204) {
            console.log("Pdf book deleted:", resp.status);
            dispatch(deletePdfBookSuccess(id));
        } else {
            dispatch(deletePdfBookFailure(`Delete failed: ${resp.status}`));
        }
    } catch (err) {
        console.error("Delete error:", err.message);
        dispatch(deletePdfBookFailure(err.message));
    }
};

export const updatePdfBook = (id, book) => async (dispatch) => {
    try {
        const response = await axios.put(`http://localhost:8080/api/v1/pdfbooks/${id}`, book);
        if (response.status === 200) {
            console.log("Success on updating pdfbook: ", response.status);
            dispatch(updatePdfBookSuccess(response.data));
        } else {
            console.log("Error on updating pdfbook: ", response.status);
            dispatch(fetchPdfBooksFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on updating pdfbook, catch section: ", error.message);
        dispatch(fetchPdfBooksFailure(error.message));
    }
};

