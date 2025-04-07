import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface Exercise {
  id: number;
  type: string;
  sets: number;
  reps: number;
  weight: number;
  date: string;
  userId: number;
}

interface ExerciseState {
  exercises: Exercise[];
  loading: boolean;
  error: string | null;
}

const initialState: ExerciseState = {
  exercises: [],
  loading: false,
  error: null,
};

export const fetchExercisesAsync = createAsyncThunk(
  'exercises/fetchExercises',
  async () => {
    const response = await axios.get('/api/exercises');
    return response.data;
  }
);

export const createExerciseAsync = createAsyncThunk(
  'exercises/createExercise',
  async (data: Omit<Exercise, 'id' | 'userId'>) => {
    const response = await axios.post('/api/exercises', data);
    return response.data;
  }
);

export const updateExerciseAsync = createAsyncThunk(
  'exercises/updateExercise',
  async ({ id, data }: { id: number; data: Partial<Exercise> }) => {
    const response = await axios.put(`/api/exercises/${id}`, data);
    return response.data;
  }
);

export const deleteExerciseAsync = createAsyncThunk(
  'exercises/deleteExercise',
  async (id: number) => {
    await axios.delete(`/api/exercises/${id}`);
    return id;
  }
);

const exerciseSlice = createSlice({
  name: 'exercises',
  initialState,
  reducers: {
    setExercises: (state, action: PayloadAction<Exercise[]>) => {
      state.exercises = action.payload;
    },
    addExercise: (state, action: PayloadAction<Exercise>) => {
      state.exercises.push(action.payload);
    },
    updateExercise: (state, action: PayloadAction<Exercise>) => {
      const index = state.exercises.findIndex(ex => ex.id === action.payload.id);
      if (index !== -1) {
        state.exercises[index] = action.payload;
      }
    },
    deleteExercise: (state, action: PayloadAction<number>) => {
      state.exercises = state.exercises.filter(ex => ex.id !== action.payload);
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchExercisesAsync.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchExercisesAsync.fulfilled, (state, action) => {
        state.loading = false;
        state.exercises = action.payload;
      })
      .addCase(fetchExercisesAsync.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Bir hata oluştu';
      })
      .addCase(createExerciseAsync.fulfilled, (state, action) => {
        state.exercises.push(action.payload);
      })
      .addCase(updateExerciseAsync.fulfilled, (state, action) => {
        const index = state.exercises.findIndex((e) => e.id === action.payload.id);
        if (index !== -1) {
          state.exercises[index] = action.payload;
        }
      })
      .addCase(deleteExerciseAsync.fulfilled, (state, action) => {
        state.exercises = state.exercises.filter((e) => e.id !== action.payload);
      });
  },
});

export const {
  setExercises,
  addExercise,
  updateExercise,
  deleteExercise,
  setLoading,
  setError,
} = exerciseSlice.actions;

export default exerciseSlice.reducer; 