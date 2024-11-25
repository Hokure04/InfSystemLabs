import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Register(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        if (username && password) {
            try {
                const response = await axios.post("http://localhost:8080/auth/register", {
                    username,
                    password,
                });
                localStorage.clear();
                localStorage.setItem("token", response.data.token);
                const userData = {
                    id: response.data.id,
                    username: response.data.username,
                    role: response.data.role,
                };
                localStorage.setItem("user", JSON.stringify(userData));
                navigate("/home");
            } catch (error) {
                console.error("Registration failed", error);
                if (error.response && error.response.status === 400) {
                    setError("Username already taken");
                } else {
                    setError("Error while registration");
                }
            }
        } else {
            setError("All fields are required");
        }
    };

    return(
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md bg-white rounded-lg shadow-md p-6">
                <h1 className="text-2xl font-bold text-center text-gray-800 mb-4">Register</h1>
                <form onSubmit={handleRegister} className="space-y-4">
                <div>
                    <label className="block text-sm font-medium text-gray-700">Username:</label>
                    <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="mt-1 w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    required
                    />
                </div>
                <div>
                    <label className="block text-sm font-medium text-gray-700">Password:</label>
                    <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="mt-1 w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    required
                    />
                </div>
                {error && <p className="text-red-500">{error}</p>}
                <button
                    type="submit"
                    className="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600"
                >
                    Register
                </button>
                </form>
                <p className="mt-4 text-center text-sm text-gray-600">
                Already have an account? <a href="/login" className="text-blue-500 hover:underline">Login</a>
                </p>
            </div>
        </div>

    );
}