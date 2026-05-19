import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "../../../config/api";

export const fetchSeries = createAsyncThunk(
    "series/fetchAll",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.series);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

const seriesSlice = createSlice({
    name: "series",
    initialState: {
        series: [],
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchSeries.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchSeries.fulfilled, (state, action) => {
                state.loading = false;
                state.series = action.payload;
            })
            .addCase(fetchSeries.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    },
});

export default seriesSlice.reducer;