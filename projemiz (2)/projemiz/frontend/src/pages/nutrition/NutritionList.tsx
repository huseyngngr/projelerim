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
import { fetchNutritionRecords, deleteNutritionRecord } from '../../store/slices/nutritionSlice';
import { RootState } from '../../store';
import NutritionForm from '../../components/nutrition/NutritionForm';
import NutritionStats from '../../components/nutrition/NutritionStats';

const NutritionList: React.FC = () => {
  const dispatch = useDispatch();
  const { nutritionRecords, loading } = useSelector(
    (state: RootState) => state.nutrition
  );
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [selectedNutrition, setSelectedNutrition] = useState<any>(null);

  useEffect(() => {
    dispatch(fetchNutritionRecords());
  }, [dispatch]);

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this nutrition record?')) {
      await dispatch(deleteNutritionRecord(id));
    }
  };

  const handleEdit = (nutrition: any) => {
    setSelectedNutrition(nutrition);
    setIsFormOpen(true);
  };

  const handleAdd = () => {
    setSelectedNutrition(null);
    setIsFormOpen(true);
  };

  const handleFormClose = () => {
    setIsFormOpen(false);
    setSelectedNutrition(null);
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
        <Typography variant="h4">Nutrition Records</Typography>
        <Button variant="contained" color="primary" onClick={handleAdd}>
          Add Nutrition Record
        </Button>
      </Box>

      <NutritionStats />

      <Divider sx={{ my: 4 }} />

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Date</TableCell>
              <TableCell>Meal Type</TableCell>
              <TableCell>Food Name</TableCell>
              <TableCell>Calories</TableCell>
              <TableCell>Protein</TableCell>
              <TableCell>Carbs</TableCell>
              <TableCell>Fat</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {nutritionRecords.map((record) => (
              <TableRow key={record.id}>
                <TableCell>
                  {new Date(record.mealDate).toLocaleDateString()}
                </TableCell>
                <TableCell>{record.mealType}</TableCell>
                <TableCell>{record.foodName}</TableCell>
                <TableCell>{record.calories}</TableCell>
                <TableCell>{record.protein}g</TableCell>
                <TableCell>{record.carbohydrates}g</TableCell>
                <TableCell>{record.fat}g</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleEdit(record)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => handleDelete(record.id)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <NutritionForm
        open={isFormOpen}
        onClose={handleFormClose}
        nutrition={selectedNutrition}
      />
    </Container>
  );
};

export default NutritionList; 