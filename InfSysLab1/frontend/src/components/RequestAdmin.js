import React, { useState } from "react";
import axios from "axios";

export default function RequestAdmin({ onClose }) {
    const [errorMessage, setErrorMessage] = useState("");

    const handleSubmit = async () => {
        const token = localStorage.getItem("token");

        try {
            const response = await axios.post(
                "http://localhost:8080/api/users/request-admin",
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            if (response.data) {
                alert("Request sent successfully!");
            } else {
                setErrorMessage("You are already an admin or request failed.");
            }
            onClose();
        } catch (error) {
            console.error("Error requesting admin:", error);
            setErrorMessage("Failed to send request.");
        }
    };

    return (
        <div className="p-6">
            <h2 className="text-2xl font-bold mb-4">Request Admin Role</h2>
            {errorMessage && <p className="text-red-500">{errorMessage}</p>}
            <div className="mt-4 flex justify-end space-x-4">
                <button
                    onClick={onClose}
                    className="bg-gray-500 text-white px-4 py-2 rounded-md"
                >
                    Cancel
                </button>
                <button
                    onClick={handleSubmit}
                    className="bg-blue-500 text-white px-4 py-2 rounded-md"
                >
                    Submit Request
                </button>
            </div>
        </div>
    );
}
