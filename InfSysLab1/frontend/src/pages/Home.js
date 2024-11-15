import React from "react";
import { useNavigate } from "react-router-dom";

export default function Home(){
    const navigate = useNavigate();
    const handleLogout = () => {
        navigate("/login");
    };

    return(
        <div>
            <h1>Home page</h1>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}