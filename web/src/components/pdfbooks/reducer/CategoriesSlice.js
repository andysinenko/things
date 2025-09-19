import {createSlice} from "@reduxjs/toolkit";


const categoriesSlice = createSlice(
    {
        name: "categories",
        initialState: {
            categories: [],
            catLoading: false,
            catError: null
        },
        reducers: {
            fetchCategoriesStart(state) {
                state.catLoading = true;
                state.carError = null;
            },

            fetchCategoriesSuccess(state, action) {
                state.loading = false;
                state.categories = action.payload;
            },

            fetchCategoriesError(state, action) {
                state.catLoading = false;
                state.carError = action.payload;
            },
        }
    }
);

export const {
    fetchCategoriesStart,
    fetchCategoriesSuccess,
    fetchCategoriesError
} = categoriesSlice.actions;

export default categoriesSlice.reducer;