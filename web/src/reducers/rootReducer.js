import {combineReducers} from 'redux';
import placeReducer from "../components/places/reducer/PlaceSlice";
import booksReducer from "../components/books/reducer/BooksSlice";
import toolsReducer from "../components/tools/reducer/ToolsSlice";


const rootReducer = combineReducers({
    placeReducer: placeReducer,
    booksReducer: booksReducer,
    toolsReducer: toolsReducer
});


export default rootReducer;