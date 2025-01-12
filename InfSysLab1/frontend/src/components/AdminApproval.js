import React, { useState, useEffect } from "react";
import axios from "axios";

export default function AdminApproval({ onClose }) {
    const [pendingUsers, setPendingUsers] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        const fetchPendingUsers = async () => {
            const token = localStorage.getItem("token");
            try {
                const response = await axios.get("http://localhost:8080/api/users/pending-approvals", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setPendingUsers(response.data);
            } catch (error) {
                console.error("Error fetching pending users:", error);
                setErrorMessage("Failed to fetch pending users.");
            }
        };

        fetchPendingUsers();
    }, []);

    const handleApprove = async (username) => {
        const token = localStorage.getItem("token");

        try {
            const response = await axios.post(
                `http://localhost:8080/api/users/approve-admin/${username}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            if (response.data) {
                alert(`${username} is now an admin!`);
                setPendingUsers(pendingUsers.filter(user => user.username !== username));
            } else {
                setErrorMessage("Failed to approve user.");
            }
        } catch (error) {
            console.error("Error approving admin:", error);
            setErrorMessage("Failed to approve user.");
        }
    };

    return (
        <div className="p-6">
            <h2 className="text-2xl font-bold mb-4">Approve Admin Requests</h2>
            {errorMessage && <p className="text-red-500">{errorMessage}</p>}
            <ul>
                {pendingUsers.map(user => (
                    <li key={user.id} className="flex justify-between items-center">
                        <span>{user.username}</span>
                        <button
                            onClick={() => handleApprove(user.username)}
                            className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
                        >
                            Approve
                        </button>
                    </li>
                ))}
            </ul>
            <div className="mt-4 flex justify-end space-x-4">
                <button
                    onClick={onClose}
                    className="bg-gray-500 text-white px-4 py-2 rounded-md"
                >
                    Close
                </button>
            </div>
        </div>
    );
}
