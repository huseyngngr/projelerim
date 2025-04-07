import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Container,
  Typography,
  Button,
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  CircularProgress,
  Divider,
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { fetchExercises, deleteExercise } from '../../store/slices/exerciseSlice';
import { RootState } from '../../store';
import ExerciseForm from '../../components/exercises/ExerciseForm';
import ExerciseStats from '../../components/exercises/ExerciseStats';

const ExerciseList: React.FC = () => {
  const dispatch = useDispatch();
  const { exercises, loading } = useSelector((state: RootState) => state.exercise);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [selectedExercise, setSelectedExercise] = useState<any>(null);

  useEffect(() => {
    dispatch(fetchExercises());
  }, [dispatch]);

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this exercise?')) {
      await dispatch(deleteExercise(id));
    }
  };

  const handleEdit = (exercise: any) => {
    setSelectedExercise(exercise);
    setIsFormOpen(true);
  };

  const handleAdd = () => {
    setSelectedExercise(null);
    setIsFormOpen(true);
  };

  const handleFormClose = () => {
    setIsFormOpen(false);
    setSelectedExercise(null);
  };

  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="60vh"
      >
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">Exercises</Typography>
        <Button variant="contained" color="primary" onClick={handleAdd}>
          Add Exercise
        </Button>
      </Box>

      <ExerciseStats />

      <Divider sx={{ my: 4 }} />

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Type</TableCell>
              <TableCell>Sets</TableCell>
              <TableCell>Reps</TableCell>
              <TableCell>Weight</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {exercises.map((exercise) => (
              <TableRow key={exercise.id}>
                <TableCell>{exercise.name}</TableCell>
                <TableCell>{exercise.type}</TableCell>
                <TableCell>{exercise.sets}</TableCell>
                <TableCell>{exercise.reps}</TableCell>
                <TableCell>{exercise.weight}kg</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleEdit(exercise)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => handleDelete(exercise.id)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <ExerciseForm
        open={isFormOpen}
        onClose={handleFormClose}
        exercise={selectedExercise}
      />
    </Container>
  );
};

export default ExerciseList; 