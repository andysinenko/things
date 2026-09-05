import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import {API_URL} from "../../../config/api.jsx";

export const fetchAllUsers = createAsyncThunk(
    "user/fetchAllUsers",
    async (_, { rejectWithValue }) => {
        const response = await axios.get(`${API_URL}/auth/user`);
        return response.data;
    }
);

export const fetchUser = createAsyncThunk(
    "user/fetchUser",
    async (_, { rejectWithValue }) => {
        const response = await axios.post(`${API_URL}/auth/user`);
        return response.data;
    }
);

export const fetchUserAuthorities = createAsyncThunk(
    "user/fetchAuthorities",
    async ({id}, { rejectWithValue }) => {
        const response = await axios.get(`${API_URL}/auth/user/${id}/authorities`);
        return response.data;
    }
);

const userSlice = createSlice({
    name: "user",
    initialState: {
        user: {},
        users: [],
        authorities: {},
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
            })
            .addCase(fetchUserAuthorities.fulfilled, (state, action) => {
                state.loading = false;
                state.authorities = action.payload.authorities;
            })
            .addCase(fetchAllUsers.fulfilled, (state, action) => {
                state.loading = false;
                state.users = action.payload;
            });
    },
});
export default userSlice.reducer;