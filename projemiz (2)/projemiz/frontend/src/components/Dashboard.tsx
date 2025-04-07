import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
    AppBar,
    Box,
    Toolbar,
    Typography,
    Button,
    Container,
    Paper,
    Grid,
} from '@mui/material';
import { RootState } from '../store';
import { logout } from '../store/slices/authSlice';
import { authService } from '../services/api';

const Dashboard: React.FC = () => {
    const dispatch = useDispatch();
    const { user } = useSelector((state: RootState) => state.auth);

    const handleLogout = () => {
        authService.logout();
        dispatch(logout());
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Healthy Life Dashboard
                    </Typography>
                    <Button color="inherit" onClick={handleLogout}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2 }}>
                            <Typography variant="h4" gutterBottom>
                                Welcome, {user?.firstName} {user?.lastName}!
                            </Typography>
                            <Typography variant="body1">
                                Email: {user?.email}
                            </Typography>
                            {user?.phoneNumber && (
                                <Typography variant="body1">
                                    Phone: {user.phoneNumber}
                                </Typography>
                            )}
                            {user?.dateOfBirth && (
                                <Typography variant="body1">
                                    Date of Birth: {new Date(user.dateOfBirth).toLocaleDateString()}
                                </Typography>
                            )}
                            {user?.gender && (
                                <Typography variant="body1">
                                    Gender: {user.gender}
                                </Typography>
                            )}
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </Box>
    );
};

export default Dashboard; 