import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import {ENDPOINTS} from "../../../config/api.jsx";

export const fetchAllUsers = createAsyncThunk(
    "user/fetchAllUsers",
    async (_, { rejectWithValue }) => {
        const response = await axios.get(`${ENDPOINTS.users}`);
        return response.data;
    }
);

export const fetchUser = createAsyncThunk(
    "user/fetchUser",
    async (_, { rejectWithValue }) => {
        const response = await axios.get(`${ENDPOINTS.users}/me`); // не /users
        return response.data;
    }
);

export const fetchUserAuthorities = createAsyncThunk(
    "user/fetchAuthorities",
    async ({id}, { rejectWithValue }) => {
        const response = await axios.get(`${ENDPOINTS.users}/${id}/authorities`);
        return response.data;
    }
);

export const updateUser = createAsyncThunk(
    "users/update",
    async ({ id, user}, { dispatch, rejectWithValue }) => {
        try {
            console.log("RAW BODY GOING TO AXIOS:", JSON.stringify(user));
            await axios.put(`${ENDPOINTS.users}/${id}`, user);
            dispatch(fetchAllUsers());
        } catch (err) {
            console.log("AXIOS ERROR RESPONSE:", err.response?.data);
            return rejectWithValue(err.message);
        }
    }
);

const userSlice = createSlice({
    name: "user",
    initialState: {
        user: {},
        users: [],
        authorities: [],
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
            })
            .addCase(updateUser.pending, (state) => {
                state.loading = true;
            })
            .addCase(updateUser.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    },
});
export default userSlice.reducer;