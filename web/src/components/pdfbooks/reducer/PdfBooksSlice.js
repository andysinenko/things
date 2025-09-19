import {createSlice} from "@reduxjs/toolkit";

const pdfBooksSlice = createSlice({
    name: "pdfbooks",
    initialState: {
        pdfbooks: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchPdfBooksStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchPdfBooksSuccess(state, action) {
            state.loading = false;
            state.pdfbooks = action.payload;
        },

        fetchPdfBooksFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        }
    },
});

export const {
    fetchPdfBooksStart,
    fetchPdfBooksSuccess,
    fetchPdfBooksFailure} =
    pdfBooksSlice.actions;

export default pdfBooksSlice.reducer;