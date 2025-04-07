import axios from 'axios';
import { store } from '../store';
import { setRecords, addRecord, updateRecord, deleteRecord, setLoading, setError } from '../store/slices/nutritionSlice';
import { authService } from './authService';

const API_URL = 'http://localhost:8080/api';

const getAuthHeader = () => ({
  headers: { Authorization: `Bearer ${authService.getToken()}` },
});

export const nutritionService = {
  async fetchRecords() {
    try {
      store.dispatch(setLoading(true));
      const response = await axios.get(`${API_URL}/nutrition`, getAuthHeader());
      store.dispatch(setRecords(response.data));
      return response.data;
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Beslenme kayıtları yüklenemedi'));
      throw error;
    } finally {
      store.dispatch(setLoading(false));
    }
  },

  async createRecord(data: {
    date: string;
    mealType: string;
    calories: number;
    protein: number;
    carbs: number;
    fat: number;
  }) {
    try {
      const response = await axios.post(`${API_URL}/nutrition`, data, getAuthHeader());
      store.dispatch(addRecord(response.data));
      return response.data;
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Beslenme kaydı oluşturulamadı'));
      throw error;
    }
  },

  async updateRecord(id: number, data: {
    date?: string;
    mealType?: string;
    calories?: number;
    protein?: number;
    carbs?: number;
    fat?: number;
  }) {
    try {
      const response = await axios.put(`${API_URL}/nutrition/${id}`, data, getAuthHeader());
      store.dispatch(updateRecord(response.data));
      return response.data;
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Beslenme kaydı güncellenemedi'));
      throw error;
    }
  },

  async deleteRecord(id: number) {
    try {
      await axios.delete(`${API_URL}/nutrition/${id}`, getAuthHeader());
      store.dispatch(deleteRecord(id));
    } catch (error) {
      store.dispatch(setError(error.response?.data?.message || 'Beslenme kaydı silinemedi'));
      throw error;
    }
  },
}; 