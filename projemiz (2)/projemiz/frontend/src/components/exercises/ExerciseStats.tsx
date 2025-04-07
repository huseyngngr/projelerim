import React from 'react';
import { useSelector } from 'react-redux';
import {
  Box,
  Paper,
  Typography,
  Grid,
} from '@mui/material';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import { RootState } from '../../store';

const ExerciseStats: React.FC = () => {
  const { exercises } = useSelector((state: RootState) => state.exercise);

  // Egzersiz tiplerine göre sayıları hesapla
  const exerciseTypeStats = exercises.reduce((acc: any, exercise) => {
    acc[exercise.type] = (acc[exercise.type] || 0) + 1;
    return acc;
  }, {});

  // Grafik verisi oluştur
  const chartData = Object.entries(exerciseTypeStats).map(([type, count]) => ({
    type,
    count,
  }));

  // Toplam egzersiz sayısı
  const totalExercises = exercises.length;

  // Ortalama set sayısı
  const averageSets = exercises.reduce((acc, exercise) => acc + exercise.sets, 0) / totalExercises || 0;

  // Ortalama tekrar sayısı
  const averageReps = exercises.reduce((acc, exercise) => acc + exercise.reps, 0) / totalExercises || 0;

  // Ortalama ağırlık
  const averageWeight = exercises.reduce((acc, exercise) => acc + exercise.weight, 0) / totalExercises || 0;

  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Exercise Statistics
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="subtitle1" gutterBottom>
              Exercise Types Distribution
            </Typography>
            <Box sx={{ height: 300 }}>
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={chartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="type" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="count" fill="#8884d8" />
                </BarChart>
              </ResponsiveContainer>
            </Box>
          </Paper>
        </Grid>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="subtitle1" gutterBottom>
              Summary Statistics
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={6}>
                <Typography variant="body2" color="text.secondary">
                  Total Exercises
                </Typography>
                <Typography variant="h4">{totalExercises}</Typography>
              </Grid>
              <Grid item xs={6}>
                <Typography variant="body2" color="text.secondary">
                  Average Sets
                </Typography>
                <Typography variant="h4">{averageSets.toFixed(1)}</Typography>
              </Grid>
              <Grid item xs={6}>
                <Typography variant="body2" color="text.secondary">
                  Average Reps
                </Typography>
                <Typography variant="h4">{averageReps.toFixed(1)}</Typography>
              </Grid>
              <Grid item xs={6}>
                <Typography variant="body2" color="text.secondary">
                  Average Weight (kg)
                </Typography>
                <Typography variant="h4">{averageWeight.toFixed(1)}</Typography>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default ExerciseStats; 