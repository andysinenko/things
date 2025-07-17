import React, { createContext, useState } from "react";
import axios from "axios";

export const AuthContext = createContext();

// Register the interceptor globally, outside the component
axios.interceptors.request.use(
    (config) => {
        const jwt = localStorage.getItem("jwtToken");
        if (jwt) {
            config.headers["Authorization"] = `Bearer ${jwt}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem("jwtToken") || "");

    return (
        <AuthContext.Provider value={{ token, setToken }}>
            {children}
        </AuthContext.Provider>
    );
};