import {createSlice} from "@reduxjs/toolkit";

const authorsSlice = createSlice({
    name: "authors",
    initialState: {
        authors: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchAuthorsStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchAuthorsSuccess(state, action) {
            state.loading = false;
            state.authors = action.payload;
        },

        fetchAuthorsFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        }
    },
});

export const {
    fetchAuthorsStart,
    fetchAuthorsSuccess,
    fetchAuthorsFailure} =
    authorsSlice.actions;

export default authorsSlice.reducer;