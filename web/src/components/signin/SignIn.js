import React, {useState} from "react";
import AppHeader from "../app-header";

const SignIn = () => {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    const handleSingIn = (e) => {
        e.preventDefault();
        console.log('Form submitted:', { username, email, password });
    }

    return (
        <div className='Container'>
            <AppHeader/>
            <div className="main-container">
                <form onSubmit={handleSingIn}>
                    <div className="text-center w-25 m-auto form-group">
                        <h3 className="m-4">Sign In</h3>
                        <input className="form-control m-4" type="email" name="email" id="email" placeholder="email"
                               onChange={(e) => setEmail(e.target.value)}/>
                        <input className="form-control m-4" type="text" placeholder="username" name="username"
                               id="username"
                               onChange={(e) => setUsername(e.target.value)}/>
                        <input className="form-control m-4" type="password" placeholder="password" name="password"
                               id="password"
                               onChange={(e) => setPassword(e.target.value)}/>

                        <button className="m-4 btn btn-info" type="submit">SignIn</button>
                    </div>
                </form>
            </div>
        </div>

    );
};

export default SignIn;

