import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "../../../config/api";
import {fetchBooks} from "./BooksSlice";

export const fetchAuthors = createAsyncThunk(
    "authors/fetchAll",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.authors);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const fetchAuthorsByGenre = createAsyncThunk(
    "authors/fetchByGenre",
    async (genreId, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.authorsByGenre(genreId));
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const addAuthor = createAsyncThunk(
    "authors/add",
    async (author, { rejectWithValue }) => {
        try {
            const response = await axios.post(ENDPOINTS.authors, author);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const updateAuthor = createAsyncThunk(
    "author/update",
    async ({ id, author }, { dispatch, rejectWithValue }) => {
        try {
            await axios.put(`${ENDPOINTS.authors}/${id}`, author);
            dispatch(fetchAuthors());
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const deleteAuthor = createAsyncThunk(
    "author/delete",
    async (id, { rejectWithValue }) => {
        try {
            await axios.delete(`${ENDPOINTS.authors}/${id}`);
            return id;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

const authorsSlice = createSlice({
    name: "authors",
    initialState: {
        authors: [],
        genreAuthors: [],
        loading: false,
        error: null,
    },
    reducers: {
        clearGenreAuthors: (state) => {
            state.genreAuthors = [];
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchAuthors.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchAuthors.fulfilled, (state, action) => {
                state.loading = false;
                state.authors = action.payload;
            })
            .addCase(fetchAuthors.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })
            .addCase(fetchAuthorsByGenre.pending, (state) => {
                state.loading = true;
            })
            .addCase(fetchAuthorsByGenre.fulfilled, (state, action) => {
                state.loading = false;
                state.genreAuthors = action.payload;
            })
            .addCase(fetchAuthorsByGenre.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })
            .addCase(addAuthor.fulfilled, (state, action) => {
                state.authors.push(action.payload);
            })
            .addCase(addAuthor.rejected, (state, action) => {
                state.error = action.payload;
            })
            .addCase(updateAuthor.pending, (state) => {
                state.loading = true;
            })
            .addCase(updateAuthor.rejected, (state, action) => {
                state.loading = false;
                state.authors.push(action.payload);
            })
            .addCase(deleteAuthor.fulfilled, (state, action) => {
                state.authors = state.authors.filter(a => a.id !== action.payload);
            })
            .addCase(deleteAuthor.rejected, (state, action) => {
                state.error = action.payload;
            });
    },
});

export const { clearGenreAuthors } = authorsSlice.actions;
export default authorsSlice.reducer;