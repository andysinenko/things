import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import {API_URL} from "../../../config/api.jsx";

export const fetchUser = createAsyncThunk(
    "user/fetchUser",
    async (_, { rejectWithValue }) => {
        const response = await axios.post(`${API_URL}/auth/user`);
        return response.data;
    }
);

const userSlice = createSlice({
    name: "user",
    initialState: {
        user: {},
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchUser.pending,   (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchUser.fulfilled, (state, action) => {
                state.loading = false;
                state.user = action.payload;
            })
            .addCase(fetchUser.rejected,  (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    },
});

export default userSlice.reducer;