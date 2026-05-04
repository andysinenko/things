import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "@/config/api";

export const fetchBooks = createAsyncThunk(
    "books/fetchAll",
    async ({ pageNumber, pageSize }, { rejectWithValue }) => {
        try {
            const response = await axios.get(`${ENDPOINTS.books}?pageNumber=${pageNumber}&pageSize=${pageSize}`);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const addNewBook = createAsyncThunk(
    "books/add",
    async (book, { rejectWithValue }) => {
        try {
            const response = await axios.post(ENDPOINTS.books, book);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const updateBook = createAsyncThunk(
    "books/update",
    async ({ id, book, pageNumber, pageSize }, { dispatch, rejectWithValue }) => {
        try {
            await axios.put(`${ENDPOINTS.books}/${id}`, book);
            dispatch(fetchBooks({ pageNumber, pageSize }));
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

export const deleteBook = createAsyncThunk(
    "books/delete",
    async (id, { rejectWithValue }) => {
        try {
            await axios.delete(`${ENDPOINTS.books}/${id}`);
            return id;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
);

const booksSlice = createSlice({
    name: "books",
    initialState: {
        books: [],
        total: 0,
        pageNumber: 0,
        loading: false,
        error: null,
    },
    reducers: {
        sortBooksById(state) {
            state.books.books.sort((a, b) => a.id - b.id);
        },
        sortBooksByIdReverse(state) {
            state.books.books.sort((a, b) => b.id - a.id);
        },
        sortBooksByTitle(state) {
            state.books.books = [...state.books.books].sort((a, b) => b.id - a.id);
        },
        sortBooksByGenre(state) {
            state.books.books.sort((a, b) => a.genre.name.localeCompare(b.genre.name));
        },
    },
    extraReducers: (builder) => {
        builder
            // fetchBooks
            .addCase(fetchBooks.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchBooks.fulfilled, (state, action) => {
                state.loading = false;
                state.books = action.payload;
            })
            .addCase(fetchBooks.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // addNewBook
            .addCase(addNewBook.fulfilled, (state, action) => {
                state.books.books.push(action.payload);
            })
            .addCase(addNewBook.rejected, (state, action) => {
                state.error = action.payload;
            })

            // updateBook
            .addCase(updateBook.pending, (state) => {
                state.loading = true;
            })
            .addCase(updateBook.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // deleteBook
            .addCase(deleteBook.fulfilled, (state, action) => {
                state.books.books = state.books.books.filter(b => b.id !== action.payload);
            })
            .addCase(deleteBook.rejected, (state, action) => {
                state.error = action.payload;
            });
    },
});

export const { sortBooksById, sortBooksByIdReverse, sortBooksByTitle, sortBooksByGenre } = booksSlice.actions;
export default booksSlice.reducer;