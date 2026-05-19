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
            });
    },
});

export default genresSlice.reducer;