import React, { useState } from "react";
import API from "../api";

export default function CreatePerson({ onClose }) {
    const [formData, setFormData] = useState({
        name: "",
        height: "",
        eyeColor: "",
        hairColor: "",
        birthday: "",
        nationality: "",
        coordinates: { x: "", y: "" },
        location: { x: "", y: "", z: "" }
    });

    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleNestedChange = (event, key) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [key]: {
                ...prevData[key],
                [name]: value,
            },
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const user = JSON.parse(localStorage.getItem("user"));
        
            if (!user || !user.id || !user.username || !user.role) {
                throw new Error("User data is missing");
            }
    
            const dataToSubmit = {
                ...formData,
                user: {
                    id: user.id,
                    username: user.username,
                    role: user.role,
                }
            };
    
            const response = await API.post("/api/persons", dataToSubmit, {
                withCredentials: true,
            });
    
            setSuccessMessage("Person created successfully!");
            setErrorMessage("");
            if (onClose) onClose();
            console.log("Response:", response.data);
        } catch (error) {
            console.error("Error creating person:", error);
            setErrorMessage("Failed to create person. Please try again.");
            setSuccessMessage("");
        }
    };
    

    return (
        <div>
            <h1>Create New Person</h1>
            {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
            {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name:</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label>Height:</label>
                    <input
                        type="number"
                        name="height"
                        value={formData.height}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label>Eye Color:</label>
                    <input
                        type="text"
                        name="eyeColor"
                        value={formData.eyeColor}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label>Hair Color:</label>
                    <input
                        type="text"
                        name="hairColor"
                        value={formData.hairColor}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label>Birthday:</label>
                    <input
                        type="date"
                        name="birthday"
                        value={formData.birthday}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label>Nationality:</label>
                    <input
                        type="text"
                        name="nationality"
                        value={formData.nationality}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <h3>Coordinates</h3>
                    <label>X:</label>
                    <input
                        type="number"
                        name="x"
                        value={formData.coordinates.x}
                        onChange={(e) => handleNestedChange(e, "coordinates")}
                    />
                    <label>Y:</label>
                    <input
                        type="number"
                        name="y"
                        value={formData.coordinates.y}
                        onChange={(e) => handleNestedChange(e, "coordinates")}
                    />
                </div>
                <div>
                    <h3>Location</h3>
                    <label>X:</label>
                    <input
                        type="number"
                        name="x"
                        value={formData.location.x}
                        onChange={(e) => handleNestedChange(e, "location")}
                    />
                    <label>Y:</label>
                    <input
                        type="number"
                        name="y"
                        value={formData.location.y}
                        onChange={(e) => handleNestedChange(e, "location")}
                    />
                    <label>Z:</label>
                    <input
                        type="number"
                        name="z"
                        value={formData.location.z}
                        onChange={(e) => handleNestedChange(e, "location")}
                    />
                </div>
                <button type="submit">Create Person</button>
            </form>
        </div>
    );
}
