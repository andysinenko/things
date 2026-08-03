import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "../../../config/api";

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
            });
    },
});

export const { clearGenreAuthors } = authorsSlice.actions;
export default authorsSlice.reducer;