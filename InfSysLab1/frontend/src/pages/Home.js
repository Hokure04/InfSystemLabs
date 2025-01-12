import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import PersonTable from "../components/PersonTable";
import CreatePerson from "../components/CreatePerson";
import AdminApproval from "../components/AdminApproval";
import RequestAdmin from "../components/RequestAdmin"; // Импорт нового компонента

Modal.setAppElement("#root");

export default function Home() {
    const navigate = useNavigate();
    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
    const [isAdminModalOpen, setIsAdminModalOpen] = useState(false);
    const [isRequestAdminModalOpen, setIsRequestAdminModalOpen] = useState(false); // Для модалки запроса на админа

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    const openCreateModal = () => setIsCreateModalOpen(true);
    const closeCreateModal = () => setIsCreateModalOpen(false);

    const openAdminModal = () => setIsAdminModalOpen(true);
    const closeAdminModal = () => setIsAdminModalOpen(false);

    const openRequestAdminModal = () => setIsRequestAdminModalOpen(true); // Открытие модалки запроса на админа
    const closeRequestAdminModal = () => setIsRequestAdminModalOpen(false); // Закрытие модалки запроса на админа

    return (
        <div className="bg-gray-100 min-h-screen">
            <header className="fixed top-0 left-0 w-full bg-blue-950 shadow-md z-10">
                <div className="p-4 flex justify-between items-center max-w-6xl mx-auto">
                    <div className="flex space-x-4">
                        <button
                            onClick={openCreateModal}
                            className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
                        >
                            Create Person
                        </button>
                        <button
                            onClick={openAdminModal}
                            className="bg-green-500 text-white py-2 px-4 rounded-md hover:bg-green-600"
                        >
                            Admin Requests
                        </button>
                        <button
                            onClick={openRequestAdminModal} // Добавляем кнопку для запроса на админа
                            className="bg-yellow-500 text-white py-2 px-4 rounded-md hover:bg-yellow-600"
                        >
                            Request Admin Role
                        </button>
                    </div>
                    <button
                        onClick={handleLogout}
                        className="bg-red-500 text-white py-2 px-4 rounded-md hover:bg-red-600"
                    >
                        Logout
                    </button>
                </div>
            </header>
            <div className="pt-20 p-6 flex flex-col items-center">
                <div className="w-full max-w-6xl">
                    <PersonTable />
                </div>
            </div>
            <Modal
                isOpen={isCreateModalOpen}
                onRequestClose={closeCreateModal}
                contentLabel="Create Person Modal"
                className="relative bg-gray-100 p-6 rounded-lg shadow-lg max-w-xl mx-auto"
                overlayClassName="fixed inset-0 bg-gray-900 bg-opacity-75 flex justify-center items-center"
            >
                <button
                    onClick={closeCreateModal}
                    className="absolute top-2 right-2 text-gray-500 hover:text-gray-800 focus:outline-none"
                    aria-label="Close modal"
                >
                    Close
                </button>
                <CreatePerson onClose={closeCreateModal} />
            </Modal>
            <Modal
                isOpen={isAdminModalOpen}
                onRequestClose={closeAdminModal}
                contentLabel="Admin Requests Modal"
                className="relative bg-gray-100 p-6 rounded-lg shadow-lg max-w-xl mx-auto"
                overlayClassName="fixed inset-0 bg-gray-900 bg-opacity-75 flex justify-center items-center"
            >
                <button
                    onClick={closeAdminModal}
                    className="absolute top-2 right-2 text-gray-500 hover:text-gray-800 focus:outline-none"
                    aria-label="Close modal"
                >
                    Close
                </button>
                <AdminApproval onClose={closeAdminModal} />
            </Modal>
            <Modal
                isOpen={isRequestAdminModalOpen} // Добавляем новый модальный компонент
                onRequestClose={closeRequestAdminModal}
                contentLabel="Request Admin Role Modal"
                className="relative bg-gray-100 p-6 rounded-lg shadow-lg max-w-xl mx-auto"
                overlayClassName="fixed inset-0 bg-gray-900 bg-opacity-75 flex justify-center items-center"
            >
                <button
                    onClick={closeRequestAdminModal}
                    className="absolute top-2 right-2 text-gray-500 hover:text-gray-800 focus:outline-none"
                    aria-label="Close modal"
                >
                    Close
                </button>
                <RequestAdmin onClose={closeRequestAdminModal} /> {/* Новый компонент */}
            </Modal>
        </div>
    );
}

