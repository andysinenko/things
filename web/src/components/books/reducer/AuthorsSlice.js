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

const authorsSlice = createSlice({
    name: "authors",
    initialState: {
        authors: [],
        loading: false,
        error: null,
    },
    reducers: {},
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
            });
    },
});

export default authorsSlice.reducer;