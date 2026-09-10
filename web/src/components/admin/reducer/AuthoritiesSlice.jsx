import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import {ENDPOINTS} from "../../../config/api.jsx";

export const fetchAuthorities = createAsyncThunk(
    "user/fetchAuthorities",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(`${ENDPOINTS.users}/authorities`);
            return response.data;
        } catch (err) {
            return rejectWithValue(err.response?.data ?? err.message);
        }
    }
);

const authoritiesSlice = createSlice({
    name: "authorities",
    initialState: {
        authorities: [],
        loading: false,
        error: null,
    },
    reducers: {
        clearAuthorities: (state) => {
            state.authorities = [];
        },},
    extraReducers: (builder) => {
        builder
            .addCase(fetchAuthorities.pending,   (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchAuthorities.fulfilled, (state, action) => {
                state.loading = false;
                state.authorities = action.payload;
            })
            .addCase(fetchAuthorities.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    },
});
export const { clearAuthorities } = authoritiesSlice.actions;
export default authoritiesSlice.reducer;