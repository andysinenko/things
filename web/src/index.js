import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './components/App';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Dashboard from "./components/dashboard";
import Places from "./components/places";
import Books from "./components/books";
import Tools from "./components/tools";
import Admin from "./components/admin";

const root = ReactDOM.createRoot(document.getElementById('root'));

const router = createBrowserRouter([
    {path:"/", element:<App/>},
    {path:"/dashboard", element:<Dashboard/>},
    {path:"/places", element:<Places/>},
    {path:"/books", element:<Books/>},
    {path:"/tools", element:<Tools/>},
    {path:"/admin", element:<Admin/>},
])


root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

//reportWebVitals();
