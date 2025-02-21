import {createSlice} from "@reduxjs/toolkit";

const toolsSlice = createSlice({
    name: "tools",
    initialState: {
        tools: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchToolsStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchToolsSuccess(state, action) {
            state.loading = false;
            state.places = action.payload;
        },

        fetchToolsFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },

        sortToolsByName: (state) => {
            state.places.sort((place1, place2) => place1.name.localeCompare(place2.name));
        },
    },
});

export const {
    fetchToolsStart,
    fetchToolsSuccess,
    sortToolsByName,
    fetchToolsFailure
} = toolsSlice.actions;

export default toolsSlice.reducer;