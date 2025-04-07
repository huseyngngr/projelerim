import React from 'react';
import { useSelector } from 'react-redux';
import {
  Box,
  Paper,
  Typography,
  Grid,
} from '@mui/material';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
} from 'recharts';
import { RootState } from '../../store';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const NutritionStats: React.FC = () => {
  const { nutritionRecords } = useSelector((state: RootState) => state.nutrition);

  // Öğün tiplerine göre kalori dağılımı
  const mealTypeCalories = nutritionRecords.reduce((acc: any, record) => {
    acc[record.mealType] = (acc[record.mealType] || 0) + record.calories;
    return acc;
  }, {});

  // Pasta grafik verisi
  const pieChartData = Object.entries(mealTypeCalories).map(([name, value]) => ({
    name,
    value,
  }));

  // Günlük kalori alımı
  const dailyCalories = nutritionRecords.reduce((acc: any, record) => {
    const date = record.mealDate.split('T')[0];
    acc[date] = (acc[date] || 0) + record.calories;
    return acc;
  }, {});

  // Çizgi grafik verisi
  const lineChartData = Object.entries(dailyCalories)
    .map(([date, calories]) => ({
      date,
      calories,
    }))
    .sort((a, b) => a.date.localeCompare(b.date));

  // Toplam istatistikler
  const totalCalories = nutritionRecords.reduce((acc, record) => acc + record.calories, 0);
  const totalProtein = nutritionRecords.reduce((acc, record) => acc + record.protein, 0);
  const totalCarbs = nutritionRecords.reduce((acc, record) => acc + record.carbohydrates, 0);
  const totalFat = nutritionRecords.reduce((acc, record) => acc + record.fat, 0);

  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Nutrition Statistics
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="subtitle1" gutterBottom>
              Daily Calorie Intake
            </Typography>
            <Box sx={{ height: 300 }}>
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={lineChartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="date" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line
                    type="monotone"
                    dataKey="calories"
                    stroke="#8884d8"
                    name="Calories"
                  />
                </LineChart>
              </ResponsiveContainer>
            </Box>
          </Paper>
        </Grid>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="subtitle1" gutterBottom>
              Calorie Distribution by Meal Type
            </Typography>
            <Box sx={{ height: 300 }}>
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={pieChartData}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    label={({ name, percent }) =>
                      `${name} ${(percent * 100).toFixed(0)}%`
                    }
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                  >
                    {pieChartData.map((entry, index) => (
                      <Cell
                        key={`cell-${index}`}
                        fill={COLORS[index % COLORS.length]}
                      />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </Box>
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="subtitle1" gutterBottom>
              Total Nutrition Summary
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={6} sm={3}>
                <Typography variant="body2" color="text.secondary">
                  Total Calories
                </Typography>
                <Typography variant="h4">{totalCalories}</Typography>
              </Grid>
              <Grid item xs={6} sm={3}>
                <Typography variant="body2" color="text.secondary">
                  Total Protein
                </Typography>
                <Typography variant="h4">{totalProtein}g</Typography>
              </Grid>
              <Grid item xs={6} sm={3}>
                <Typography variant="body2" color="text.secondary">
                  Total Carbs
                </Typography>
                <Typography variant="h4">{totalCarbs}g</Typography>
              </Grid>
              <Grid item xs={6} sm={3}>
                <Typography variant="body2" color="text.secondary">
                  Total Fat
                </Typography>
                <Typography variant="h4">{totalFat}g</Typography>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default NutritionStats; 