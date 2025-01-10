import {combineReducers} from 'redux';
import placeReducer from "../components/places/reducer/placeReducer";


const rootReducer = combineReducers({
    placeReducer: placeReducer
});


export default rootReducer;