
import {applyMiddleware, configureStore} from '@reduxjs/toolkit';
import rootReducer from '../reducers/rootReducer';
import {composeWithDevTools} from "redux-devtools-extension";
import {thunk} from "redux-thunk";
import { createBrowserHistory } from 'history'


const history = createBrowserHistory();

const store = configureStore({
    reducer: rootReducer,
    middleware: ()=>    [thunk],
    history: history,
    devTools: composeWithDevTools()
});

export default store;