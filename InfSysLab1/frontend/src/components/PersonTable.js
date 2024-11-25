import React, { useState, useEffect, useMemo } from "react";
import API from "../api";

export default function PersonTable() {
    const [persons, setPersons] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const pageSize = 10;

    useEffect(() => {
        const fetchPersons = async () => {
            try {
                console.log("Fetching persons...");
                const response = await API.get("/api/persons"); // Используем API
                console.log("Response data:", response.data);
                setPersons(response.data);
            } catch (error) {
                console.error("Error fetching persons:", error.response || error);
            }
        };

        fetchPersons();
    }, []);

    const totalPages = useMemo(() => Math.ceil(persons.length / pageSize), [persons]);

    const paginate = (array, page, size) => {
        const start = page * size;
        return array.slice(start, start + size);
    };

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setCurrentPage(newPage);
        }
    };

    const currentPersons = paginate(persons, currentPage, pageSize);

    if (!persons.length) {
        return <div>No data available</div>;
    }

    return (
        <div>
            <h1>Persons</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Height</th>
                        <th>Eye Color</th>
                        <th>Hair Color</th>
                        <th>Birthday</th>
                        <th>Nationality</th>
                    </tr>
                </thead>
                <tbody>
                    {currentPersons.map((person) => (
                        <tr key={person.id}>
                            <td>{person.id}</td>
                            <td>{person.name}</td>
                            <td>{person.height}</td>
                            <td>{person.eyeColor}</td>
                            <td>{person.hairColor}</td>
                            <td>{person.birthday}</td>
                            <td>{person.nationality}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div>
                <button 
                    onClick={() => handlePageChange(currentPage - 1)} 
                    disabled={currentPage === 0}
                >
                    Previous
                </button>
                <span>Page {currentPage + 1} of {totalPages}</span>
                <button 
                    onClick={() => handlePageChange(currentPage + 1)} 
                    disabled={currentPage === totalPages - 1}
                >
                    Next
                </button>
            </div>
        </div>
    );
}
