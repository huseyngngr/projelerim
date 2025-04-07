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
import { createExercise, updateExercise } from '../../store/slices/exerciseSlice';

interface ExerciseFormProps {
  open: boolean;
  onClose: () => void;
  exercise?: {
    id: number;
    name: string;
    type: string;
    sets: number;
    reps: number;
    weight: number;
    notes?: string;
  };
}

const exerciseTypes = [
  'Strength',
  'Cardio',
  'Flexibility',
  'Balance',
  'Sports',
];

const ExerciseForm: React.FC<ExerciseFormProps> = ({
  open,
  onClose,
  exercise,
}) => {
  const dispatch = useDispatch();
  const [formData, setFormData] = useState({
    name: '',
    type: '',
    sets: 0,
    reps: 0,
    weight: 0,
    notes: '',
  });

  useEffect(() => {
    if (exercise) {
      setFormData({
        name: exercise.name,
        type: exercise.type,
        sets: exercise.sets,
        reps: exercise.reps,
        weight: exercise.weight,
        notes: exercise.notes || '',
      });
    } else {
      setFormData({
        name: '',
        type: '',
        sets: 0,
        reps: 0,
        weight: 0,
        notes: '',
      });
    }
  }, [exercise]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'type' ? value : Number(value) || value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (exercise) {
      await dispatch(updateExercise({ id: exercise.id, data: formData }));
    } else {
      await dispatch(createExercise(formData));
    }
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        {exercise ? 'Edit Exercise' : 'Add New Exercise'}
      </DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                label="Exercise Name"
                name="name"
                value={formData.name}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                select
                label="Exercise Type"
                name="type"
                value={formData.type}
                onChange={handleChange}
              >
                {exerciseTypes.map((type) => (
                  <MenuItem key={type} value={type}>
                    {type}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={12} sm={4}>
              <TextField
                required
                fullWidth
                type="number"
                label="Sets"
                name="sets"
                value={formData.sets}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <TextField
                required
                fullWidth
                type="number"
                label="Reps"
                name="reps"
                value={formData.reps}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <TextField
                required
                fullWidth
                type="number"
                label="Weight (kg)"
                name="weight"
                value={formData.weight}
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
            {exercise ? 'Update' : 'Add'}
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default ExerciseForm; 