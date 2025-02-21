import {createSlice} from "@reduxjs/toolkit";

const placeSlice = createSlice({
    name: "places",
    initialState: {
        places: [],
        loading: false,
        error: null,
    },
    reducers: {
        fetchPlacesStart(state) {
            state.loading = true;
            state.error = null;
        },

        fetchPlacesSuccess(state, action) {
            state.loading = false;
            state.places = action.payload;
        },

        fetchPlacesFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },

        sortPlacesByName: (state) => {
            state.places.sort((place1, place2) => place1.name.localeCompare(place2.name));
        },
    },
});

export const {
    fetchPlacesStart,
    fetchPlacesSuccess,
    sortPlacesByName,
    fetchPlacesFailure
} = placeSlice.actions;

export default placeSlice.reducer;