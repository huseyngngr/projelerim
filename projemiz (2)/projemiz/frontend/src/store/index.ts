import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import exerciseReducer from './slices/exerciseSlice';
import nutritionReducer from './slices/nutritionSlice';

export const store = configureStore({
    reducer: {
        auth: authReducer,
        exercises: exerciseReducer,
        nutrition: nutritionReducer,
    },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch; 