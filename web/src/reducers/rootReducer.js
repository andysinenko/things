import {combineReducers} from 'redux';
import placeReducer from "../components/places/reducer/placeReducer";
import booksReducer from "../components/books/reducer/BooksSlice";


const rootReducer = combineReducers({
    placeReducer: placeReducer,
    booksReducer: booksReducer
});


export default rootReducer;