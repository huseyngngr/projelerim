import React from 'react';
import { Container, Typography } from '@mui/material';
import NutritionList from './nutrition/NutritionList';

const NutritionTracking: React.FC = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" gutterBottom>
        Beslenme Takibi
      </Typography>
      <NutritionList />
    </Container>
  );
};

export default NutritionTracking; 