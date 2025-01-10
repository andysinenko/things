import {API_LOADING, FETCH_PLACE_SUCCESS, FETCH_PLACES_SUCCESS} from "./actionTypes";


const INITIAL_STATE = {
    places: [],
    loading: false
}


const placeReducer = (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case FETCH_PLACES_SUCCESS:
            return {...state, places: action.payload};
        case FETCH_PLACE_SUCCESS:
            return {...state, places: action.payload};
        case API_LOADING:
            return Object.assign({}, state, { loading: action.loading });
        default:
            return state;
    }
};

export default placeReducer;