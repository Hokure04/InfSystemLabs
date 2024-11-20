import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; 
import axios from "axios";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLegion = async (e) => {
    e.preventDefault();
    if (username && password) {
      try {
        const response = await axios.post("http://localhost:8080/auth/login", {
          username,
          password,
        });
        
        // Сохраняем токен в localStorage
        localStorage.setItem("token", response.data.token);
        navigate("/home");
      } catch (error) {
        console.error("Login failed", error);
        alert("Invalid credentials");
      }
    }
  };

  return (
    <div>
      <h1>Login page</h1>
      <form onSubmit={handleLegion}>
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
