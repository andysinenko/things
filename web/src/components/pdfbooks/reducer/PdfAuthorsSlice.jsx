import {createSlice} from "@reduxjs/toolkit";

const pdfAuthorsSlice = createSlice({
    name: "pdfauthors",
    initialState: {
        pdfauthors: [],
        pdfAuthLoading: false,
        pdfAuthError: null,
    },
    reducers: {
        fetchPdfAuthorsStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchPdfAuthorsSuccess(state, action) {
            state.loading = false;
            state.pdfauthors = action.payload;
        },

        fetchPdfAuthorsFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        }
    },
});

export const {
    fetchPdfAuthorsStart,
    fetchPdfAuthorsSuccess,
    fetchPdfAuthorsFailure} =
    pdfAuthorsSlice.actions;

export default pdfAuthorsSlice.reducer;