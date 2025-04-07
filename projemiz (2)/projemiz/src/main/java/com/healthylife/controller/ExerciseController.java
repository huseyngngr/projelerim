package com.healthylife.controller;

import com.healthylife.dto.ExerciseDTO;
import com.healthylife.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(exerciseService.getExercisesByUserId(userId));
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(exerciseService.getExercisesByDateRange(userId, start, end));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ExerciseDTO> createExercise(
            @PathVariable Long userId,
            @RequestBody ExerciseDTO exerciseDTO) {
        return ResponseEntity.ok(exerciseService.createExercise(userId, exerciseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> updateExercise(
            @PathVariable Long id,
            @RequestBody ExerciseDTO exerciseDTO) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, exerciseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.ok().build();
    }
} 