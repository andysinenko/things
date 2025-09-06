import {createSlice} from "@reduxjs/toolkit";


const initialState = {
    books: {
        books: [],
        total: 0,
        pageNumber: 0,
        loading: false,
        error: null,
    },
};

const booksSlice = createSlice({
    name: "books",
    initialState,
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

        deleteBook(state, action) {
            const bookId = action.payload;
            state.books = state.booksReducer.books.filter(book => book.id !== bookId);
        },

        updateBookStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateBookSuccess(state, action) {
            state.loading = false;
            state.error = null;
            state.books = state.booksReducer.books.map(book =>
                book.id === action.payload.id ? action.payload : book
            );
        },
        updateBookFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },

        sortBooksByTitle(state) {
            console.log(state.books);
            state.books.books = [...state.books.books].sort((a, b) => b.id - a.id);
        },

        sortBooksById: (state) => {
            state.books.books.sort((book1, book2) => book1.id - book2.id);
        },

        sortBooksByIdReverse: (state) => {
            state.books.books.sort((book1, book2) => book2.id - book1.id);
        },
        sortBooksByGenre: (state) => {
            state.books.books.sort((book1, book2) => book1.genre.name.localeCompare(book2.genre.name));
        },

        deleteBookSuccess(state, action) {
            state.loading = false;
            state.books = action.payload;
        },

        deleteBookFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    fetchBooksStart,
    fetchBooksSuccess,
    fetchBooksFailure,
    deleteBook,
    updateBookStart,
    updateBookSuccess,
    updateBookFailure,
    sortBooksByTitle,
    sortBooksById,
    sortBooksByIdReverse,
    sortBooksByGenre,
    deleteBookSuccess,
    deleteBookFailure} =
    booksSlice.actions;

export default booksSlice.reducer;