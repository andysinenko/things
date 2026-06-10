import {createSlice} from "@reduxjs/toolkit";

const initialState = {
    pdfbooks: {
        pdfbooks: [],
        pageNumber: 0,
    },
    loading: false,
    error: null,
};

const pdfBooksSlice = createSlice({
    name: "pdfbooks",
    initialState: initialState,
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
        },

        deletePdfBookSuccess(state, action) {
            state.loading = false;
            const bookIdToDelete = action.payload;
            state.pdfbooks.pdfbooks = state.pdfbooks.pdfbooks.filter(
                (book) => book.id !== bookIdToDelete
            );
        },

        deletePdfBookFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },

        addPdfBookSuccess(state, action) {
            state.loading = false;
            state.pdfbooks.pdfbooks.push(action.payload);
        },

        updatePdfBookSuccess: (state, action) => {
            const updated = action.payload;
            const idx = state.pdfbooks.content.findIndex(b => b.id === updated.id);
            if (idx !== -1) {
                state.pdfbooks.content[idx] = updated;
            }
        },
    },
});

export const {
    fetchPdfBooksStart,
    fetchPdfBooksSuccess,
    fetchPdfBooksFailure,
    deletePdfBookSuccess,
    deletePdfBookFailure,
    addPdfBookSuccess,
    updatePdfBookSuccess} =
    pdfBooksSlice.actions;

export default pdfBooksSlice.reducer;