import axios from "axios";

const API = axios.create({
  method: "GET",
  baseURL: "http://localhost:8080",
  withCredentials: true,
});

// Добавляем токен в заголовки
API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  console.log("Token retrieved from localStorage:", token); // Проверяем, загружается ли токен
  if (token) {
      config.headers.Authorization = `Bearer ${token}`;
  } else {
      console.error("Token is missing in localStorage!");
  }
  return config;
});


export default API;
