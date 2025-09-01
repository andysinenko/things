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
            state.tools = action.payload;
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
} = toolsSlice.actions;

export default toolsSlice.reducer;