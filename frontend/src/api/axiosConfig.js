import axios from 'axios';

// Ajusta el puerto al que estés usando en Spring Boot (generalmente 8080)
export const api = axios.create({
    baseURL: 'https://ubiquitous-parakeet-qjj9gjvvwwwf9xvx-8080.app.github.dev/',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Interceptor para manejar errores globalmente (opcional pero recomendado)
api.interceptors.response.use(
    response => response,
    error => {
        console.error("Error en la API:", error.response?.data || error.message);
        return Promise.reject(error);
    }
);