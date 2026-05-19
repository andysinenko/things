import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "../../../config/api";


export const fetchTools = createAsyncThunk(
    "tools/fetchAll",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.tools);
            return response.data;
        } catch (error) {
            return rejectWithValue(error.message);
        }
    }
);

export const addNewTool = createAsyncThunk(
    "tools/add",
    async (tool, { dispatch, rejectWithValue }) => {
        try {
            await axios.post(ENDPOINTS.tools, tool);
            dispatch(fetchTools());
        } catch (error) {
            return rejectWithValue(error.message);
        }
    }
);

export const updateTool = createAsyncThunk(
    "tools/update",
    async ({ id, tool }, { dispatch, rejectWithValue }) => {
        try {
            await axios.put(`${ENDPOINTS.tools}/${id}`, tool);
            dispatch(fetchTools());
        } catch (error) {
            return rejectWithValue(error.message);
        }
    }
);

export const deleteTool = createAsyncThunk(
    "tools/delete",
    async (id, { rejectWithValue }) => {
        try {
            await axios.delete(`${ENDPOINTS.tools}/${id}`);
            return id;
        } catch (error) {
            return rejectWithValue(error.message);
        }
    }
);

const toolsSlice = createSlice({
    name: "tools",
    initialState: {
        tools: [],
        loading: false,
        error: null,
    },
    reducers: {
        sortById(state) {
            state.tools = [...state.tools].sort((a, b) => a.id - b.id);
        },
        sortByName(state) {
            state.tools = [...state.tools].sort((a, b) => a.name.localeCompare(b.name));
        },
        sortByBrand(state) {
            state.tools = [...state.tools].sort((a, b) =>
                a.vendor?.name.localeCompare(b.vendor?.name)
            );
        },
        sortByType(state) {
            state.tools = [...state.tools].sort((a, b) =>
                a.toolType.localeCompare(b.toolType)
            );
        },
    },
    extraReducers: (builder) => {
        builder
            // fetchTools
            .addCase(fetchTools.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchTools.fulfilled, (state, action) => {
                state.loading = false;
                state.tools = action.payload.sort((a, b) => a.id - b.id);
            })
            .addCase(fetchTools.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })

            // deleteTool
            .addCase(deleteTool.fulfilled, (state, action) => {
                state.tools = state.tools.filter(tool => tool.id !== action.payload);
            })
            .addCase(deleteTool.rejected, (state, action) => {
                state.error = action.payload;
            })

            // addNewTool
            .addCase(addNewTool.rejected, (state, action) => {
                state.error = action.payload;
            })

            // updateTool
            .addCase(updateTool.rejected, (state, action) => {
                state.error = action.payload;
            });
    },
});

export const { sortById, sortByName, sortByBrand, sortByType } = toolsSlice.actions;

export default toolsSlice.reducer;