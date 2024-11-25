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
        <div className="p-6 flex flex-col items-center bg-gray-100">
            <div className="flex justify-between items-center mb-6 w-full max-w-6xl">
                <button
                    onClick={handleLogout}
                    className="bg-red-500 text-white py-2 px-4 rounded-md hover:bg-red-600"
                >
                    Logout
                </button>
            </div>
            <div className="w-full max-w-6xl">
                <PersonTable />
            </div>
            <div className="w-full max-w-6xl mt-8">
                <CreatePerson />
            </div>
        </div>
    );
}