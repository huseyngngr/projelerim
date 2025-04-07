import React from 'react';
import { Container, Typography } from '@mui/material';
import ExerciseList from './exercises/ExerciseList';

const ExerciseTracking: React.FC = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" gutterBottom>
        Egzersiz Takibi
      </Typography>
      <ExerciseList />
    </Container>
  );
};

export default ExerciseTracking; 