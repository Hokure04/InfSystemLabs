import React, { useState, useEffect, useMemo } from "react";
import API from "../api";

export default function PersonTable() {
    const [persons, setPersons] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [filters, setFilters] = useState({
        name: "",
        eyeColor: "",
        hairColor: "",
        nationality: "",
    });
    const [sortConfig, setSortConfig] = useState({ key: "id", direction: "asc" });
    const [editingFilter, setEditingFilter] = useState(null);
    const pageSize = 10;

    useEffect(() => {
        const fetchPersons = async () => {
            try {
                const response = await API.get("/api/persons");
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

    const filteredPersons = persons.filter((person) => {
        return (
            (filters.name === "" || person.name.toLowerCase() === filters.name.toLowerCase()) &&
            (filters.eyeColor === "" || person.eyeColor.toLowerCase() === filters.eyeColor.toLowerCase()) &&
            (filters.hairColor === "" || person.hairColor.toLowerCase() === filters.hairColor.toLowerCase()) &&
            (filters.nationality === "" || person.nationality.toLowerCase() === filters.nationality.toLowerCase())
        );
    });

    const sortedPersons = useMemo(() => {
        const sorted = [...filteredPersons];
        sorted.sort((a, b) => {
            if (a[sortConfig.key] < b[sortConfig.key]) {
                return sortConfig.direction === "asc" ? -1 : 1;
            }
            if (a[sortConfig.key] > b[sortConfig.key]) {
                return sortConfig.direction === "asc" ? 1 : -1;
            }
            return 0;
        });
        return sorted;
    }, [filteredPersons, sortConfig]);

    const currentPersons = paginate(sortedPersons, currentPage, pageSize);

    const handleSort = (key) => {
        let direction = "asc";
        if (sortConfig.key === key && sortConfig.direction === "asc") {
            direction = "desc";
        }
        setSortConfig({ key, direction });
    };

    const handleFilterChange = (e, column) => {
        setFilters({
            ...filters,
            [column]: e.target.value,
        });
    };

    const handleFilterDoubleClick = (column) => {
        setEditingFilter(column);
    };

    const handleFilterBlur = () => {
        setEditingFilter(null);
    };

    const clearFilter = (column) => {
        setFilters({
            ...filters,
            [column]: "",
        });
    };

    if (!persons.length) {
        return <div>No data available</div>;
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-3xl font-bold text-brand-dark mb-8">Persons</h1>

            <div className="overflow-x-auto w-full max-w-5xl">
                <table className="table-auto w-full bg-white shadow-md rounded-lg">
                    <thead className="bg-brand text-white">
                        <tr>
                            {["id", "name", "height", "eyeColor", "hairColor", "nationality"].map((col) => (
                                <th
                                    key={col}
                                    className="px-4 py-2 text-left relative"
                                >
                                    <div
                                        onDoubleClick={() =>
                                            ["name", "eyeColor", "hairColor", "nationality"].includes(col)
                                                ? handleFilterDoubleClick(col)
                                                : null
                                        }
                                        className="flex items-center"
                                    >
                                        <span>{col[0].toUpperCase() + col.slice(1)}</span>
                                        <button
                                            className="ml-2 text-xs"
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                handleSort(col);
                                            }}
                                        >
                                            {sortConfig.key === col
                                                ? sortConfig.direction === "asc"
                                                    ? "⬆️"
                                                    : "⬇️"
                                                : "⬍"}
                                        </button>
                                    </div>
                                    {["name", "eyeColor", "hairColor", "nationality"].includes(col) && (
                                        <div className="mt-1">
                                            {editingFilter === col ? (
                                                <input
                                                    type="text"
                                                    value={filters[col]}
                                                    onChange={(e) => handleFilterChange(e, col)}
                                                    onBlur={handleFilterBlur}
                                                    autoFocus
                                                    className="mt-1 px-2 py-1 rounded border border-gray-300 text-black"
                                                    style={{ width: "50%" }}
                                                />
                                            ) : (
                                                <>
                                                    {filters[col] || ""}
                                                    {filters[col] && (
                                                        <button
                                                            onClick={(e) => {
                                                                e.stopPropagation();
                                                                clearFilter(col);
                                                            }}
                                                            className="ml-2 text-xs text-red-500"
                                                        >
                                                            Clear
                                                        </button>
                                                    )}
                                                </>
                                            )}
                                        </div>
                                    )}
                                </th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {currentPersons.map((person, index) => (
                            <tr
                                key={person.id}
                                className={index % 2 === 0 ? "bg-gray-100" : "bg-gray-50"}
                            >
                                <td className="px-4 py-2">{person.id}</td>
                                <td className="px-4 py-2">{person.name}</td>
                                <td className="px-4 py-2">{person.height}</td>
                                <td className="px-4 py-2">{person.eyeColor}</td>
                                <td className="px-4 py-2">{person.hairColor}</td>
                                <td className="px-4 py-2">{person.nationality}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <div className="flex items-center justify-between mt-4 w-full max-w-5xl">
                <button
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                    className="px-4 py-2 bg-brand-light text-white rounded-lg hover:bg-brand"
                >
                    Previous
                </button>
                <span className="text-gray-700">
                    Page {currentPage + 1} of {totalPages}
                </span>
                <button
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={currentPage === totalPages - 1}
                    className="px-4 py-2 bg-brand-light text-white rounded-lg hover:bg-brand"
                >
                    Next
                </button>
            </div>
        </div>
    );
}



