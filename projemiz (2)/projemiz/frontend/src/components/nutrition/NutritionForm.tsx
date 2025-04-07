import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Grid,
  MenuItem,
} from '@mui/material';
import { createNutritionRecord, updateNutritionRecord } from '../../store/slices/nutritionSlice';

interface NutritionFormProps {
  open: boolean;
  onClose: () => void;
  nutrition?: {
    id: number;
    mealType: string;
    foodName: string;
    calories: number;
    protein: number;
    carbohydrates: number;
    fat: number;
    notes?: string;
    mealDate: string;
  };
}

const mealTypes = ['Breakfast', 'Lunch', 'Dinner', 'Snack'];

const NutritionForm: React.FC<NutritionFormProps> = ({
  open,
  onClose,
  nutrition,
}) => {
  const dispatch = useDispatch();
  const [formData, setFormData] = useState({
    mealType: '',
    foodName: '',
    calories: 0,
    protein: 0,
    carbohydrates: 0,
    fat: 0,
    notes: '',
    mealDate: new Date().toISOString().split('T')[0],
  });

  useEffect(() => {
    if (nutrition) {
      setFormData({
        mealType: nutrition.mealType,
        foodName: nutrition.foodName,
        calories: nutrition.calories,
        protein: nutrition.protein,
        carbohydrates: nutrition.carbohydrates,
        fat: nutrition.fat,
        notes: nutrition.notes || '',
        mealDate: nutrition.mealDate.split('T')[0],
      });
    } else {
      setFormData({
        mealType: '',
        foodName: '',
        calories: 0,
        protein: 0,
        carbohydrates: 0,
        fat: 0,
        notes: '',
        mealDate: new Date().toISOString().split('T')[0],
      });
    }
  }, [nutrition]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'mealType' || name === 'foodName' || name === 'notes' || name === 'mealDate'
        ? value
        : Number(value) || 0,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (nutrition) {
      await dispatch(updateNutritionRecord({ id: nutrition.id, data: formData }));
    } else {
      await dispatch(createNutritionRecord(formData));
    }
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        {nutrition ? 'Edit Nutrition Record' : 'Add New Nutrition Record'}
      </DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                type="date"
                label="Date"
                name="mealDate"
                value={formData.mealDate}
                onChange={handleChange}
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                select
                label="Meal Type"
                name="mealType"
                value={formData.mealType}
                onChange={handleChange}
              >
                {mealTypes.map((type) => (
                  <MenuItem key={type} value={type}>
                    {type}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                label="Food Name"
                name="foodName"
                value={formData.foodName}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                type="number"
                label="Calories"
                name="calories"
                value={formData.calories}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                type="number"
                label="Protein (g)"
                name="protein"
                value={formData.protein}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                type="number"
                label="Carbohydrates (g)"
                name="carbohydrates"
                value={formData.carbohydrates}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                type="number"
                label="Fat (g)"
                name="fat"
                value={formData.fat}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                multiline
                rows={3}
                label="Notes"
                name="notes"
                value={formData.notes}
                onChange={handleChange}
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose}>Cancel</Button>
          <Button type="submit" variant="contained">
            {nutrition ? 'Update' : 'Add'}
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default NutritionForm; 