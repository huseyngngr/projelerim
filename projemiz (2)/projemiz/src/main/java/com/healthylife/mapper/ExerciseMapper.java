package com.healthylife.mapper;

import com.healthylife.dto.ExerciseDTO;
import com.healthylife.model.Exercise;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public ExerciseDTO toDTO(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setUserId(exercise.getUser().getId());
        dto.setExerciseType(exercise.getExerciseType());
        dto.setDuration(exercise.getDuration());
        dto.setCaloriesBurned(exercise.getCaloriesBurned());
        dto.setDistance(exercise.getDistance());
        dto.setHeartRate(exercise.getHeartRate());
        dto.setIntensity(exercise.getIntensity());
        dto.setNotes(exercise.getNotes());
        dto.setExerciseDate(exercise.getExerciseDate());
        dto.setCreatedAt(exercise.getCreatedAt());
        dto.setUpdatedAt(exercise.getUpdatedAt());
        return dto;
    }

    public Exercise toEntity(ExerciseDTO dto) {
        Exercise exercise = new Exercise();
        exercise.setId(dto.getId());
        exercise.setExerciseType(dto.getExerciseType());
        exercise.setDuration(dto.getDuration());
        exercise.setCaloriesBurned(dto.getCaloriesBurned());
        exercise.setDistance(dto.getDistance());
        exercise.setHeartRate(dto.getHeartRate());
        exercise.setIntensity(dto.getIntensity());
        exercise.setNotes(dto.getNotes());
        exercise.setExerciseDate(dto.getExerciseDate());
        return exercise;
    }
} 