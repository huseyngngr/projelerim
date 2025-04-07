export interface Exercise {
    id: number;
    userId: number;
    name: string;
    type: string;
    sets: number;
    reps: number;
    weight: number;
    duration: number;
    caloriesBurned: number;
    exerciseDate: string;
    notes: string;
    createdAt: string;
    updatedAt: string;
}

export interface Nutrition {
    id: number;
    userId: number;
    mealType: string;
    foodName: string;
    calories: number;
    protein: number;
    carbohydrates: number;
    fat: number;
    mealDate: string;
    notes: string;
    createdAt: string;
    updatedAt: string;
}

export interface User {
    id: number;
    username: string;
    email: string;
    fullName: string;
}

export interface AuthState {
    user: User | null;
    token: string | null;
    loading: boolean;
    error: string | null;
}

export interface ExerciseState {
    exercises: Exercise[];
    loading: boolean;
    error: string | null;
}

export interface NutritionState {
    nutritionRecords: Nutrition[];
    loading: boolean;
    error: string | null;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface SignupRequest extends LoginRequest {
    firstName: string;
    lastName: string;
}

export interface AuthResponse {
    token: string;
    user: User;
}

export interface MessageResponse {
    message: string;
} 