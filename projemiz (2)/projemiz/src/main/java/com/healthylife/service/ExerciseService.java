package com.healthylife.service;

import com.healthylife.dto.ExerciseDTO;
import com.healthylife.model.Exercise;
import com.healthylife.model.User;
import com.healthylife.repository.ExerciseRepository;
import com.healthylife.repository.UserRepository;
import com.healthylife.mapper.ExerciseMapper;
import com.healthylife.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ExerciseMapper exerciseMapper;

    @Transactional
    public ExerciseDTO createExercise(Long userId, ExerciseDTO exerciseDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
        exercise.setUser(user);

        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDTO(savedExercise);
    }

    public List<ExerciseDTO> getAllExercises(Long userId) {
        List<Exercise> exercises = exerciseRepository.findByUserId(userId);
        return exercises.stream()
            .map(exerciseMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(Long userId, Long exerciseId) {
        Exercise exercise = exerciseRepository.findByIdAndUserId(exerciseId, userId)
            .orElseThrow(() -> new RuntimeException("Exercise not found"));
        return exerciseMapper.toDTO(exercise);
    }

    @Transactional
    public ExerciseDTO updateExercise(Long userId, Long exerciseId, ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseRepository.findByIdAndUserId(exerciseId, userId)
            .orElseThrow(() -> new RuntimeException("Exercise not found"));

        exercise.setExerciseType(exerciseDTO.getExerciseType());
        exercise.setDuration(exerciseDTO.getDuration());
        exercise.setCaloriesBurned(exerciseDTO.getCaloriesBurned());
        exercise.setDistance(exerciseDTO.getDistance());
        exercise.setHeartRate(exerciseDTO.getHeartRate());
        exercise.setIntensity(exerciseDTO.getIntensity());
        exercise.setNotes(exerciseDTO.getNotes());
        exercise.setExerciseDate(exerciseDTO.getExerciseDate());

        Exercise updatedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDTO(updatedExercise);
    }

    @Transactional
    public void deleteExercise(Long userId, Long exerciseId) {
        Exercise exercise = exerciseRepository.findByIdAndUserId(exerciseId, userId)
            .orElseThrow(() -> new RuntimeException("Exercise not found"));
        exerciseRepository.delete(exercise);
    }

    @Transactional(readOnly = true)
    public List<ExerciseDTO> getExercisesByUserId(Long userId) {
        return exerciseRepository.findByUserIdOrderByExerciseDateDesc(userId).stream()
                .map(exerciseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseDTO> getExercisesByDateRange(Long userId, LocalDateTime start, LocalDateTime end) {
        return exerciseRepository.findByUserIdAndExerciseDateBetween(userId, start, end).stream()
                .map(exerciseMapper::toDTO)
                .collect(Collectors.toList());
    }
} 