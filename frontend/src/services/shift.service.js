import { api } from '../api/axiosConfig';

export const getShifts = async () => {
    const response = await api.get('/shifts');
    return response.data;
};

export const createShift = async (shiftRequestDTO) => {
    const response = await api.post('/shifts', shiftRequestDTO);
    return response.data;
};