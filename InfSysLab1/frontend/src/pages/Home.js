import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import PersonTable from "../components/PersonTable";
import CreatePerson from "../components/CreatePerson";

Modal.setAppElement('#root');

export default function Home(){
    const navigate = useNavigate();
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);

    return(
        <div className="bg-gray-100 min-h-screen">
            <header className="fixed top-0 left-0 w-full bg-blue-950 shdow-md z-10">
                <div className="p-4 flex justify-between items-center max-w-6xl mx-auto">
                    <div className="flex space-x-4">
                        <button
                            onClick={openModal}
                            className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
                        >
                            CreatePerson
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
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                shouldCloseOverlayClick={false}
                contentLabel="Create Person Modal"
                className="relative bg-gray-100 p-6 rounded-lg shadow-lg max-w-xl mx-auto"
                overlayClassName="fixed inset-0 bg-gray-900 bg-opacity-75 flex justify-center items-center"    
            >
                <button
                    onClick={closeModal}
                    className="absolute top-2 right-2 text-gray-500 hover:text-gray-800 focus:outline-none"
                    aria-label="close modal"
                >
                    Close
                </button>
                <CreatePerson onClose={closeModal} />
            </Modal>
        </div>
    );
}