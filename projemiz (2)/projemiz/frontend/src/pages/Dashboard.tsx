import React from 'react';
import { useSelector } from 'react-redux';
import { Container, Typography, Grid, Paper, Box } from '@mui/material';
import { RootState } from '../store';

const Dashboard: React.FC = () => {
  const { user } = useSelector((state: RootState) => state.auth);

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" gutterBottom>
        Hoş Geldiniz, {user?.username}!
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 240 }}>
            <Typography variant="h6" gutterBottom>
              Son Egzersizler
            </Typography>
            {/* Egzersiz istatistikleri buraya gelecek */}
          </Paper>
        </Grid>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 240 }}>
            <Typography variant="h6" gutterBottom>
              Beslenme Özeti
            </Typography>
            {/* Beslenme istatistikleri buraya gelecek */}
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
            <Typography variant="h6" gutterBottom>
              Haftalık İlerleme
            </Typography>
            {/* İlerleme grafiği buraya gelecek */}
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard; 