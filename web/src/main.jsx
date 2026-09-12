import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import './modal.css';
import App from './components/App';
import Places from "./components/places";
import Books from "./components/books";
import Tools from "./components/tools";
import Admin from "./components/admin";
import {Provider} from "react-redux";
import store from "./store/storeConfig";

import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import SignIn from "./components/signin/SignIn";
import {AuthProvider} from "./components/auth/AuthProvider";
import {PdfBooks} from "./components/pdfbooks/PdfBooks";
import {Dashboard} from "./components/dashboard/Dashboard";
import CatalogPage from "./components/catalog/CatalogPage";
import AuthorsCatalog from "./components/catalog/AuthorsCatalog";
import SeriesCatalog from "./components/catalog/SeriesCatalog";
import GenresCatalog from "./components/catalog/GenresCatalog";
import {RequireAdmin} from "./components/auth/RequireAdmin.jsx";
import {RequireAuth} from "./components/auth/RequireAuth.jsx";

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <Provider store={store}>
            <AuthProvider>
                <Router>
                    <Routes>
                        <Route path="/" element={<App/>} >
                            <Route index element={<Navigate to="/signin" replace />} />
                            <Route path="/dashboard" element={<Dashboard/>}/>
                            <Route path="/places" element={<Places/>}/>
                            <Route path="/books" element={<Books/>}/>
                            <Route path="/tools" element={<Tools/>}/>
                            <Route path="/pdfbook" element={<PdfBooks/>}/>
                            <Route path="/catalog" element={<CatalogPage/>} >
                                <Route index element={<Navigate to="authors" replace />} />
                                <Route path="authors" element={<AuthorsCatalog/>}/>
                                <Route path="series" element={<SeriesCatalog/>}/>
                                <Route path="genres" element={<GenresCatalog/>}/>
                            </Route>
                            <Route path="/admin" element={<RequireAuth><RequireAdmin><Admin/></RequireAdmin></RequireAuth>} />
                        </Route>
                        <Route path="/signin" element={<SignIn />} />
                    </Routes>
                </Router>
            </AuthProvider>
        </Provider>
    </React.StrictMode>
);

