import {createSlice} from "@reduxjs/toolkit";

const brandsSlice = createSlice({
    name: "brands",
    initialState: {
        tools: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchBrandsStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchBrandsSuccess(state, action) {
            state.loading = false;
            state.places = action.payload;
        },

        fetchBrandsFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    fetchBrandsStart,
    fetchBrandsSuccess,
    fetchBrandsFailure
} = brandsSlice.actions;

export default brandsSlice.reducer;