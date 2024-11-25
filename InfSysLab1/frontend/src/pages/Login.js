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
    <div>
      <h1>Login page</h1>
      <form onSubmit={handleLogin}>
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
        <button type="submit">Login</button>
      </form>
    </div>
  );
}
