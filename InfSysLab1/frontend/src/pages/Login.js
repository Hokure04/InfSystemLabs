import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; 
import axios from "axios";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    if (username && password) {
      try {
        const response = await axios.post("http://localhost:8080/auth/login", {
          username,
          password,
        });
  
        console.log("Login response:", response.data);
  
        if (response.data.token) {
          localStorage.setItem("token", response.data.token);
          console.log("Token saved in localStorage:", localStorage.getItem("token"));
          
          const userData = {
            id: response.data.id,
            username: response.data.username,
            role: response.data.role,
          };
          localStorage.setItem("user", JSON.stringify(userData));
          console.log("User saved in localStorage:", userData);
  
          navigate("/home");
        } else {
          alert("No token received");
        }
      } catch (error) {
        console.error("Login failed", error);
        alert("Invalid credentials");
      }
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md bg-white rounded-lg shadow-md p-6">
      <h1 className="text-2xl font-bold text-center text-gray-800 mb-4">Login page</h1>
      <form onSubmit={handleLogin} className="space-y-4">
        <div>
        <label className="block text-sm font-medium text-gray-700">
          Username:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="mt-1 w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </label>
        </div>
        <br />
        <div>
        <label className="block text-sm font-medium text-gray-700">
          Password:
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="mt-1 w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </label>
        </div>
        <br />
        <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600">Login</button>
      </form>
    </div>
    </div>
  );
}
