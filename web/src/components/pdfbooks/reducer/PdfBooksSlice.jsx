import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";
import {ENDPOINTS} from "../../../config/api";

const initialState = {
    pdfbooks: {
        pdfbooks: [],
        pageNumber: 0,
        total: 0,
    },
    categories: [],
    pdfAuthors: [],
    loading: false,
    error: null,
    count: 0,
};

export const getPdfBooksCount = createAsyncThunk(
    "pdfbooks/getPdfBooksCount",
    async () => {
        try {
            const response = await axios.get(`${ENDPOINTS.pdfbooks}/count`);
            return response.data;
        } catch (err) {
            console.log(err.message);
            return 0;
        }
    }
);

export const fetchPdfBooks = createAsyncThunk(
    "pdfbooks/fetchPdfBooks",
    async ({pageNumber, pageSize}, {rejectWithValue}) => {
        try {
            const response = await axios.get(
                `${ENDPOINTS.pdfbooks}?pageNumber=${pageNumber}&pageSize=${pageSize}`
            );
            console.log("pdfbooks response.data: " + response.data);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const fetchCategories = createAsyncThunk(
    "pdfbooks/fetchCategories",
    async (_, {rejectWithValue}) => {
        try {
            const response = await axios.get(`${ENDPOINTS.pdfbooks}/categories`);
            if (response.status === 200) {
                return response.data;
            }
            return rejectWithValue(`Error: ${response.status}`);
        } catch (error) {
            console.log("Error on fetching categories, catch section: ", error.message);
            if (error.config) {
                console.log("Request headers on error:", error.config.headers);
            }
            return rejectWithValue(error.message);
        }
    }
);

export const fetchPdfAuthors = createAsyncThunk(
    "pdfbooks/fetchPdfAuthors",
    async (_, {rejectWithValue}) => {
        try {
            const response = await axios.get(`${ENDPOINTS.pdfbooks}/pdfauthors`);
            if (response.status === 200) {
                return response.data;
            }
            console.log("Error on fetching pdf authors: ", response.status);
            return rejectWithValue(`Error: ${response.status}`);
        } catch (error) {
            console.log("Error on fetching pdf authors, catch section: ", error.message);
            if (error.config) {
                console.log("Request headers on error:", error.config.headers);
            }
            return rejectWithValue(error.message);
        }
    }
);

export const uploadPdfBook = createAsyncThunk(
    "pdfbooks/uploadPdfBook",
    async (formData, {rejectWithValue}) => {
        try {
            const response = await axios.post(`${ENDPOINTS.pdfbooks}/upload`, formData);
            if (response.status === 200 || response.status === 201) {
                console.log("Success on adding new pdfbook: ", response.status);
                return response.data;
            }
            console.log("Error on adding new pdfbook: ", response.status);
            return rejectWithValue(`Error: ${response.status}`);
        } catch (error) {
            console.log("Error on adding new pdfbook, catch section: ", error.message);
            return rejectWithValue(error.message);
        }
    }
);

export const deletePdfBook = createAsyncThunk(
    "pdfbooks/deletePdfBook",
    async (id, {rejectWithValue}) => {
        try {
            const resp = await axios.delete(`${ENDPOINTS.pdfbooks}/${id}`);
            if (resp.status === 200 || resp.status === 204) {
                console.log("Pdf book deleted:", resp.status);
                return id;
            }
            return rejectWithValue(`Delete failed: ${resp.status}`);
        } catch (err) {
            console.error("Delete error:", err.message);
            return rejectWithValue(err.message);
        }
    }
);

export const updatePdfBook = createAsyncThunk(
    "pdfbooks/updatePdfBook",
    async ({id, book}, {rejectWithValue}) => {
        try {
            const response = await axios.put(`${ENDPOINTS.pdfbooks}/${id}`, book);
            if (response.status === 200) {
                console.log("Success on updating pdfbook: ", response.status);
                return response.data;
            }
            console.log("Error on updating pdfbook: ", response.status);
            return rejectWithValue(`Error: ${response.status}`);
        } catch (error) {
            console.log("Error on updating pdfbook, catch section: ", error.message);
            return rejectWithValue(error.message);
        }
    }
);

const pdfBooksSlice = createSlice({
    name: "pdfbooks",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            // fetchPdfBooks
            .addCase(getPdfBooksCount.fulfilled, (state, action) => {
                state.loading = false;
                state.count = action.payload;
                state.error = null;
            })
            .addCase(fetchPdfBooks.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchPdfBooks.fulfilled, (state, action) => {
                state.loading = false;
                state.pdfbooks = action.payload;
            })
            .addCase(fetchPdfBooks.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // fetchCategories
            .addCase(fetchCategories.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchCategories.fulfilled, (state, action) => {
                state.loading = false;
                state.categories = action.payload;
            })
            .addCase(fetchCategories.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // fetchPdfAuthors
            .addCase(fetchPdfAuthors.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchPdfAuthors.fulfilled, (state, action) => {
                state.loading = false;
                state.pdfAuthors = action.payload;
            })
            .addCase(fetchPdfAuthors.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // uploadPdfBook
            .addCase(uploadPdfBook.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(uploadPdfBook.fulfilled, (state, action) => {
                state.loading = false;
                state.pdfbooks.pdfbooks.push(action.payload);
            })
            .addCase(uploadPdfBook.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // deletePdfBook
            .addCase(deletePdfBook.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(deletePdfBook.fulfilled, (state, action) => {
                state.loading = false;
                const bookIdToDelete = action.payload;
                state.pdfbooks.pdfbooks = state.pdfbooks.pdfbooks.filter(
                    (book) => book.id !== bookIdToDelete
                );
            })
            .addCase(deletePdfBook.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // updatePdfBook
            .addCase(updatePdfBook.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(updatePdfBook.fulfilled, (state, action) => {
                state.loading = false;
                const updated = action.payload;
                const idx = state.pdfbooks.content.findIndex((b) => b.id === updated.id);
                if (idx !== -1) {
                    state.pdfbooks.content[idx] = updated;
                }
            })
            .addCase(updatePdfBook.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    },
});

export default pdfBooksSlice.reducer;