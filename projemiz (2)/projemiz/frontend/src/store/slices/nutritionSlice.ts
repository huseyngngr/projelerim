import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface NutritionRecord {
  id: number;
  date: string;
  mealType: string;
  calories: number;
  protein: number;
  carbs: number;
  fat: number;
  userId: number;
}

interface NutritionState {
  records: NutritionRecord[];
  loading: boolean;
  error: string | null;
}

const initialState: NutritionState = {
  records: [],
  loading: false,
  error: null,
};

const nutritionSlice = createSlice({
  name: 'nutrition',
  initialState,
  reducers: {
    setRecords: (state, action: PayloadAction<NutritionRecord[]>) => {
      state.records = action.payload;
    },
    addRecord: (state, action: PayloadAction<NutritionRecord>) => {
      state.records.push(action.payload);
    },
    updateRecord: (state, action: PayloadAction<NutritionRecord>) => {
      const index = state.records.findIndex(record => record.id === action.payload.id);
      if (index !== -1) {
        state.records[index] = action.payload;
      }
    },
    deleteRecord: (state, action: PayloadAction<number>) => {
      state.records = state.records.filter(record => record.id !== action.payload);
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
});

export const {
  setRecords,
  addRecord,
  updateRecord,
  deleteRecord,
  setLoading,
  setError,
} = nutritionSlice.actions;

export default nutritionSlice.reducer; 