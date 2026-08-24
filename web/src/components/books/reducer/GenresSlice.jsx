import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "../../../config/api";

export const fetchGenres = createAsyncThunk(
    "genres/fetchAll",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.genres);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const addGenre = createAsyncThunk(
    "genres/add",
    async (genre, { rejectWithValue }) => {
        try {
            const response = await axios.post(ENDPOINTS.genres, genre);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const updateGenre = createAsyncThunk(
    "genres/update",
    async ({ id, genre }, { dispatch, rejectWithValue }) => {
        try {
            await axios.put(`${ENDPOINTS.genres}/${id}`, genre);
            dispatch(fetchGenres());
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const deleteGenre = createAsyncThunk(
    "genres/delete",
    async (id, { rejectWithValue }) => {
        try {
            await axios.delete(`${ENDPOINTS.genres}/${id}`);
            return id;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

const genresSlice = createSlice({
    name: "genres",
    initialState: {
        genres: [],
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchGenres.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchGenres.fulfilled, (state, action) => {
                state.loading = false;
                state.genres = action.payload;
            })
            .addCase(fetchGenres.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })
            .addCase(addGenre.fulfilled, (state, action) => {
                state.genres.push(action.payload);
            })
            .addCase(addGenre.rejected, (state, action) => {
                state.error = action.payload;
            })
            .addCase(updateGenre.pending, (state) => {
                state.loading = true;
            })
            .addCase(updateGenre.rejected, (state, action) => {
                state.loading = false;
                state.genres.push(action.payload);
            })
            .addCase(deleteGenre.fulfilled, (state, action) => {
                state.genres = state.genres.filter(g => g.id !== action.payload);
            })
            .addCase(deleteGenre.rejected, (state, action) => {
                state.error = action.payload;
            });
    },
});

export default genresSlice.reducer;