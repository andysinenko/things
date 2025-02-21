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

        sortBooksByTitle: (state) => {
            state.books.sort((book1, book2) => book1.title.localeCompare(book2.title));
        },

        sortBooksById: (state) => {
            state.books.sort((book1, book2) => book1.id - book2.id);
        },

        sortBooksByIdReverse: (state) => {
            state.books.sort((book1, book2) => book2.id - book1.id);
        },
        sortBooksByGenre: (state) => {
            state.books.sort((book1, book2) => book1.genre.name.localeCompare(book2.genre.name));
        },
    },
});

export const {fetchBooksStart,
    fetchBooksSuccess,
    fetchBooksFailure,
    sortBooksByTitle,
    sortBooksById,
    sortBooksByIdReverse,
    sortBooksByGenre} =
    booksSlice.actions;

export default booksSlice.reducer;