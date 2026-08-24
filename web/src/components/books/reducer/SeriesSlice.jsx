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

export const addSeries = createAsyncThunk(
    "series/add",
    async (series, { rejectWithValue }) => {
        try {
            const response = await axios.post(ENDPOINTS.series, series);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const updateSeries = createAsyncThunk(
    "series/update",
    async ({ id, series }, { dispatch, rejectWithValue }) => {
        try {
            await axios.put(`${ENDPOINTS.series}/${id}`, series);
            dispatch(fetchSeries());
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const deleteSeries = createAsyncThunk(
    "series/delete",
    async (id, { rejectWithValue }) => {
        try {
            await axios.delete(`${ENDPOINTS.series}/${id}`);
            return id;
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
            })
            .addCase(addSeries.fulfilled, (state, action) => {
                state.series.push(action.payload);
            })
            .addCase(addSeries.rejected, (state, action) => {
                state.error = action.payload;
            })
            .addCase(updateSeries.pending, (state) => {
                state.loading = true;
            })
            .addCase(updateSeries.rejected, (state, action) => {
                state.loading = false;
                state.series.push(action.payload);
            })
            .addCase(deleteSeries.fulfilled, (state, action) => {
                state.series = state.series.filter(g => g.id !== action.payload);
            })
            .addCase(deleteSeries.rejected, (state, action) => {
                state.error = action.payload;
            });
    },
});

export default seriesSlice.reducer;