import {combineReducers} from 'redux';
import placeReducer from "../components/places/reducer/PlaceSlice";
import booksReducer from "../components/books/reducer/BooksSlice";
import toolsReducer from "../components/tools/reducer/ToolsSlice";
import useReducer from "../components/signin/reducer/UserSlice";
import authorsReducer from "../components/books/reducer/AuthorsSlice";
import genresReducer from "../components/books/reducer/GenresSlice";
import seriesReducer from "../components/books/reducer/SeriesSlice";
import brandsReducer from "../components/tools/reducer/BrandsSlice";


const rootReducer = combineReducers({
    placeReducer: placeReducer,
    booksReducer: booksReducer,
    toolsReducer: toolsReducer,
    userReducer: useReducer,
    authorsReducer: authorsReducer,
    genresReducer: genresReducer,
    seriesReducer: seriesReducer,
    brandsReducer: brandsReducer
});


export default rootReducer;