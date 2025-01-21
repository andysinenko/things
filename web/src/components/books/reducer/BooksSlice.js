import {createSlice} from "@reduxjs/toolkit";

const booksSlice = createSlice({
    name: "books",
    initialState: {
        books: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchBooksStart(state) {
            state.loading = true;
            state.error = null;
        },
        fetchBooksSuccess(state, action) {
            state.loading = false;
            state.books = action.payload;
        },
        fetchBooksFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const { fetchBooksStart, fetchBooksSuccess, fetchBooksFailure } =
    booksSlice.actions;

export default booksSlice.reducer;