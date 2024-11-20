import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Register(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleRegister = async (e) =>{
        e.preventDefault();
        if(username && password){
            try{
                const response = await axios.post("http://localhost:8080/auth/register", {
                    username,
                    password,
                });
                localStorage.setItem("token", response.data);
                navigate("/home");
            }catch(error){
                console.error("Registration failed", error)
                if(error.response && error.response.status === 400){
                    setError("Username already taken");
                }else{
                    setError("Error while registration");
                }
            }
        } else{
            setError("All fields are required");
        }
    };

    return(
        <div>
            <h1>Register</h1>
            <form onSubmit={handleRegister} >
                {error && <p style={{ color: "red"}}>{error}</p>}
                <label>
                    Username:
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </label>
                <br />
                <label>
                    Password:
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </label>
                <br />
                <button type="submit">Register</button>
            </form>
            <p>
                Already have an account? <a href="/login">Login here</a>
            </p>
        </div>
    );
}