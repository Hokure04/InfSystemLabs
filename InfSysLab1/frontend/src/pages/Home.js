import React from "react";
import { useNavigate } from "react-router-dom";
import PersonTable from "../components/PersonTable";
import CreatePerson from "../components/CreatePerson";

export default function Home(){
    const navigate = useNavigate();
    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return(
        <div>
            <h1>Home page</h1>
            <button onClick={handleLogout}>Logout</button>
            <PersonTable />
            <CreatePerson />
        </div>
    );
}