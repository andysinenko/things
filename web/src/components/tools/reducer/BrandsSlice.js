import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "@/config/api";

export const fetchBrands = createAsyncThunk(
    "brands/fetchAll",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.brands);
            return response.data;
        } catch (error) {
        }
    }
);

const brandsSlice = createSlice({
    name: "brands",
    initialState: {
        brands: [],
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchBrands.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchBrands.fulfilled, (state, action) => {
                state.loading = false;
                state.brands = action.payload;
            })
            .addCase(fetchBrands.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    },
});

export default brandsSlice.reducer;