import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import Login from "./pages/Login";
import Home from "./pages/Home";
import Register from "./pages/Register";

function App() {
  return (
    <div className="min-h-screen bg-gray-50">
      <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />}/>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} /> 
      </Routes>
    </Router>
    </div>
  );
}

export default App;
