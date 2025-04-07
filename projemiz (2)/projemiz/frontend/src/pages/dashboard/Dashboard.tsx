import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import {
  Container,
  Grid,
  Paper,
  Typography,
  Box,
  CircularProgress,
} from '@mui/material';
import { RootState } from '../../store';

const Dashboard: React.FC = () => {
  const { user } = useSelector((state: RootState) => state.auth);
  const { exercises, loading: exercisesLoading } = useSelector(
    (state: RootState) => state.exercises
  );
  const { nutritionRecords, loading: nutritionLoading } = useSelector(
    (state: RootState) => state.nutrition
  );

  const totalCalories = nutritionRecords.reduce(
    (sum, record) => sum + record.calories,
    0
  );

  const totalProtein = nutritionRecords.reduce(
    (sum, record) => sum + record.protein,
    0
  );

  const totalExercises = exercises.length;

  if (exercisesLoading || nutritionLoading) {
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
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Typography variant="h4" gutterBottom>
            Welcome, {user?.username}!
          </Typography>
        </Grid>
        <Grid item xs={12} md={4}>
          <Paper
            sx={{
              p: 2,
              display: 'flex',
              flexDirection: 'column',
              height: 140,
            }}
          >
            <Typography component="h2" variant="h6" color="primary" gutterBottom>
              Total Exercises
            </Typography>
            <Typography component="p" variant="h4">
              {totalExercises}
            </Typography>
          </Paper>
        </Grid>
        <Grid item xs={12} md={4}>
          <Paper
            sx={{
              p: 2,
              display: 'flex',
              flexDirection: 'column',
              height: 140,
            }}
          >
            <Typography component="h2" variant="h6" color="primary" gutterBottom>
              Total Calories
            </Typography>
            <Typography component="p" variant="h4">
              {totalCalories}
            </Typography>
          </Paper>
        </Grid>
        <Grid item xs={12} md={4}>
          <Paper
            sx={{
              p: 2,
              display: 'flex',
              flexDirection: 'column',
              height: 140,
            }}
          >
            <Typography component="h2" variant="h6" color="primary" gutterBottom>
              Total Protein
            </Typography>
            <Typography component="p" variant="h4">
              {totalProtein}g
            </Typography>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard; 