import axios from 'axios';
import { LoginRequest, SignupRequest, AuthResponse, MessageResponse, User } from '../types';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const authService = {
    login: async (credentials: LoginRequest): Promise<AuthResponse> => {
        const response = await api.post<AuthResponse>('/auth/signin', credentials);
        return response.data;
    },

    signup: async (data: SignupRequest): Promise<MessageResponse> => {
        const response = await api.post<MessageResponse>('/auth/signup', data);
        return response.data;
    },

    logout: async (): Promise<void> => {
        await api.post('/auth/signout');
    }
};

export const userService = {
    getCurrentUser: async (): Promise<User> => {
        const response = await api.get<User>('/users/me');
        return response.data;
    },

    updateUser: async (id: number, data: Partial<User>): Promise<User> => {
        const response = await api.put<User>(`/users/${id}`, data);
        return response.data;
    },
};

export default api; 