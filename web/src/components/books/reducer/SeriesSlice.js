import {createSlice} from "@reduxjs/toolkit";

const seriesSlice = createSlice({
    name: "series",
    initialState: {
        series: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchSeriesStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchSeriesSuccess(state, action) {
            state.loading = false;
            state.series = action.payload;
        },

        fetchSeriesFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        }
    },
});

export const {
    fetchSeriesStart,
    fetchSeriesSuccess,
    fetchSeriesFailure}
    = seriesSlice.actions;

export default seriesSlice.reducer;