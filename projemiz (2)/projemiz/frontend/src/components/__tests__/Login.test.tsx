import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import Login from '../Login';
import authReducer from '../../store/slices/authSlice';

const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate
}));

const createTestStore = (initialState = {}) => {
    return configureStore({
        reducer: {
            auth: authReducer
        },
        preloadedState: {
            auth: {
                user: null,
                token: null,
                isAuthenticated: false,
                loading: false,
                error: null,
                ...initialState
            }
        }
    });
};

describe('Login Component', () => {
    const renderLogin = (store = createTestStore()) => {
        return render(
            <Provider store={store}>
                <BrowserRouter>
                    <Login />
                </BrowserRouter>
            </Provider>
        );
    };

    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should render login form', () => {
        renderLogin();
        expect(screen.getByText('Hesabınıza giriş yapın')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('E-posta adresi')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Şifre')).toBeInTheDocument();
        expect(screen.getByText('Giriş Yap')).toBeInTheDocument();
    });

    it('should show loading spinner when submitting', async () => {
        const store = createTestStore({ loading: true });
        renderLogin(store);
        
        const submitButton = screen.getByText('Giriş Yap');
        fireEvent.click(submitButton);

        expect(screen.getByRole('status')).toBeInTheDocument();
    });

    it('should show error message when login fails', async () => {
        const errorMessage = 'Geçersiz kimlik bilgileri';
        const store = createTestStore({ error: errorMessage });
        renderLogin(store);

        expect(screen.getByText(errorMessage)).toBeInTheDocument();
    });

    it('should navigate to dashboard on successful login', async () => {
        const store = createTestStore({
            isAuthenticated: true,
            user: {
                id: 1,
                email: 'test@example.com',
                firstName: 'Test',
                lastName: 'User',
                enabled: true
            }
        });

        renderLogin(store);

        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledWith('/dashboard');
        });
    });

    it('should handle form submission', async () => {
        renderLogin();

        const emailInput = screen.getByPlaceholderText('E-posta adresi');
        const passwordInput = screen.getByPlaceholderText('Şifre');
        const submitButton = screen.getByText('Giriş Yap');

        fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
        fireEvent.change(passwordInput, { target: { value: 'password123' } });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(emailInput).toHaveValue('test@example.com');
            expect(passwordInput).toHaveValue('password123');
        });
    });
}); 