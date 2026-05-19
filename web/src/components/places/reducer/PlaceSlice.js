import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { ENDPOINTS } from "../../../config/api";

export const fetchAllPlaces = createAsyncThunk(
    "places/fetchAll",
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get(ENDPOINTS.places);
            return response.data;
        } catch (error) {
            return rejectWithValue(error.message);
        }
    }
);

const placeSlice = createSlice({
    name: "places",
    initialState: {
        places: [],
        loading: false,
        error: null,
    },
    reducers: {
        sortPlacesByName: (state) => {
            state.places.sort((place1, place2) => place1.name.localeCompare(place2.name));
        },

        sortPlacesById: (state) => {
            state.places.sort((place1, place2) => place1.id - place2.id);
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchAllPlaces.pending, (state) => {
                state.loading = true;
                state.error = null
            })
            .addCase(fetchAllPlaces.fulfilled, (state, action) => {
                state.loading = false;
                state.places = action.payload;
            })
            .addCase(fetchAllPlaces.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    }
});

export const {sortPlacesByName, sortPlacesById,} = placeSlice.actions;

export default placeSlice.reducer;