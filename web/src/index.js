import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import './modal.css';
import App from './components/App';
import Places from "./components/places";
import Books from "./components/books";
import Tools from "./components/tools";
//import Admin from "./components/admin";
import {Provider} from "react-redux";
import store from "./store/storeConfig";
import Dashboard from "./components/dashboard";

import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import SignIn from "./components/signin/SignIn";
import {AuthProvider} from "./components/auth/AuthProvider";
import {PdfBooks} from "./components/pdfbooks/PdfBooks";


const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <Provider store={store}>
        <React.StrictMode>
            <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<App/>} exect={true}>
                        <Route path="/dashboard" element={<Dashboard/>}/>
                        <Route path="/places" element={<Places/>}/>
                        <Route path="/books" element={<Books/>}/>
                        <Route path="/tools" element={<Tools/>}/>
                        <Route path="/pdfbook" element={<PdfBooks/>}/>
                    </Route>
                    <Route path="/signin" element={<SignIn />} />
                </Routes>
            </Router>
            </AuthProvider>
        </React.StrictMode>
    </Provider>
);

