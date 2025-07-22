import {createSlice} from "@reduxjs/toolkit";

const genresSlice = createSlice({
    name: "genres",
    initialState: {
        genres: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchGenresStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchGenresSuccess(state, action) {
            state.loading = false;
            state.genres = action.payload;
        },

        fetchGenresFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        }
    },
});

export const {
    fetchGenresStart,
    fetchGenresSuccess,
    fetchGenresFailure} =
    genresSlice.actions;

export default genresSlice.reducer;