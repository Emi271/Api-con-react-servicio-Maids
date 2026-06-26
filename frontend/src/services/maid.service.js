import { api } from '../api/axiosConfig';

export const getMaids = async () => {
    const response = await api.get('/maids');
    return response.data;
};

export const getMaidById = async (id) => {
    const response = await api.get(`/maids/${id}`);
    return response.data;
};

export const createMaid = async (maidRequestDTO) => {
    const response = await api.post('/maids', maidRequestDTO);
    return response.data;
};

// Asumiendo que tienes un endpoint para listar las skills disponibles
export const getSkills = async () => {
    const response = await api.get('/skills'); 
    return response.data;
};