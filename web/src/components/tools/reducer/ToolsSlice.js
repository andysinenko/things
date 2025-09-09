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
            state.tools = action.payload.sort((a, b) => a.id - b.id);
            state.error = null;
        },
        fetchToolsFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },
        deleteToolSuccess(state, action) {
            state.loading = false;
            state.error = null;
            state.tools = state.tools.filter(tool => tool.id !== action.payload);
        },
        deleteToolFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },

        addToolSuccess(state, action) {
            state.loading = false;
            state.error = null;
            state.tools.push(action.payload);
        },

        updateToolStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateToolSuccess(state, action) {
            state.loading = false;
            state.error = null;
            state.tools = state.tools.map(tool =>
                tool.id === action.payload.id ? action.payload : tool
            );
        },
        updateToolFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },

        sortById(state) {
            state.tools = [...state.tools].sort((a, b) => a.id - b.id);
        },

        sortByName(state) {
            state.tools = [...state.tools].sort((a, b) => a.name.localeCompare(b.name));
        },

        sortByBrand(state) {
            state.tools = [...state.tools].sort((a, b) => a.vendor?.name.localeCompare(b.vendor?.name));
        },

        sortByType(state) {
            state.tools = [...state.tools].sort((a, b) => a.toolType.localeCompare(b.toolType));
        },
    },
});

export const {
    fetchToolsStart,
    fetchToolsSuccess,
    fetchToolsFailure,
    deleteToolSuccess,
    deleteToolFailure,
    updateToolFailure,
    updateToolStart,
    updateToolSuccess,
    addToolSuccess,
    sortById,
    sortByName,
    sortByBrand,
    sortByType
} = toolsSlice.actions;

export default toolsSlice.reducer;