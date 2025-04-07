import axios from 'axios';
import { store } from '../store';
import { loginSuccess, loginFailure, logout } from '../store/slices/authSlice';

const API_URL = 'http://localhost:8080/api';

export const authService = {
  async login(username: string, password: string) {
    try {
      const response = await axios.post(`${API_URL}/auth/login`, {
        username,
        password,
      });
      const { user, token } = response.data;
      store.dispatch(loginSuccess({ user, token }));
      return response.data;
    } catch (error) {
      store.dispatch(loginFailure(error.response?.data?.message || 'Giriş başarısız'));
      throw error;
    }
  },

  async register(username: string, email: string, password: string, fullName: string) {
    try {
      const response = await axios.post(`${API_URL}/auth/register`, {
        username,
        email,
        password,
        fullName,
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  logout() {
    store.dispatch(logout());
  },

  getCurrentUser() {
    const state = store.getState();
    return state.auth.user;
  },

  getToken() {
    const state = store.getState();
    return state.auth.token;
  },
}; 