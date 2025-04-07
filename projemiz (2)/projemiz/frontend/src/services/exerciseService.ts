import axios from 'axios';
import { store } from '../store';
import { setExercises, addExercise, updateExercise, deleteExercise, setLoading, setError } from '../store/slices/exerciseSlice';
import { authService } from './authService';

const API_URL = 'http://localhost:8080/api';

const getAuthHeader = () => ({
  headers: { Authorization: `Bearer ${authService.getToken()}` },
});

export const exerciseService = {
  async fetchExercises() {
    try {
      store.dispatch(setLoading(true));
      const response = await axios.get(`${API_URL}/exercises`, getAuthHeader());
      store.dispatch(setExercises(response.data));
      return response.data;
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Egzersizler yüklenemedi'));
      throw error;
    } finally {
      store.dispatch(setLoading(false));
    }
  },

  async createExercise(data: {
    type: string;
    sets: number;
    reps: number;
    weight: number;
    date: string;
  }) {
    try {
      const response = await axios.post(`${API_URL}/exercises`, data, getAuthHeader());
      store.dispatch(addExercise(response.data));
      return response.data;
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Egzersiz oluşturulamadı'));
      throw error;
    }
  },

  async updateExercise(id: number, data: {
    type?: string;
    sets?: number;
    reps?: number;
    weight?: number;
    date?: string;
  }) {
    try {
      const response = await axios.put(`${API_URL}/exercises/${id}`, data, getAuthHeader());
      store.dispatch(updateExercise(response.data));
      return response.data;
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Egzersiz güncellenemedi'));
      throw error;
    }
  },

  async deleteExercise(id: number) {
    try {
      await axios.delete(`${API_URL}/exercises/${id}`, getAuthHeader());
      store.dispatch(deleteExercise(id));
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Egzersiz silinemedi'));
      throw error;
    }
  },
}; 