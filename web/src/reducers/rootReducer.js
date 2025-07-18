import {combineReducers} from 'redux';
import placeReducer from "../components/places/reducer/PlaceSlice";
import booksReducer from "../components/books/reducer/BooksSlice";
import toolsReducer from "../components/tools/reducer/ToolsSlice";
import useReducer from "../components/signin/reducer/UserSlice";


const rootReducer = combineReducers({
    placeReducer: placeReducer,
    booksReducer: booksReducer,
    toolsReducer: toolsReducer,
    userReducer: useReducer
});


export default rootReducer;