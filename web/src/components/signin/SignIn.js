import React, {useState, useContext} from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { AuthContext } from "../auth/AuthProvider";

import './SignIn.css';
import {useDispatch} from "react-redux";
import {fetchUser} from "./api/api"; // Assuming you have a CSS file for styling

const SignIn = () => {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const { setToken } = useContext(AuthContext);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleSignIn = async (e) => {
        e.preventDefault();
        setError("");
        try {
            const response = await axios.post("http://localhost:8080/api/v1/auth/authenticate", {
                email,
                username,
                password
            });
            const token = response.data.accessToken || response.headers['authorization'] || response.headers['Authorization'];
            if (token) {
                localStorage.setItem("jwtToken", token);
                setToken(token);
                navigate("/dashboard");
                fetchUser(dispatch);
            } else {
                setError("No token received");
            }
        } catch (err) {
            setError("Authentication failed: " + (err.response?.data?.message || err.message));
        }
    };

    return (
        <div className="signin-root">
            <div className="signin-header">
                <span className="title">Things</span>
            </div>
            <form onSubmit={handleSignIn}>
                <input className="form-control m-4" type="text" placeholder="username" name="username"
                       id="username"
                       onChange={(e) => setUsername(e.target.value)}/>
                <input className="form-control m-4" type="password" placeholder="password" name="password"
                       id="password"
                       onChange={(e) => setPassword(e.target.value)}/>
                <button className="m-4 btn-signin" type="submit">Sign In</button>
                {error && <div className="alert alert-danger">{error}</div>}
            </form>
        </div>
    );
};

export default SignIn;