package com.healthylife.repository;

import com.healthylife.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByUserId(Long userId);
    Optional<Exercise> findByIdAndUserId(Long id, Long userId);
    List<Exercise> findByUserIdOrderByExerciseDateDesc(Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
    List<Exercise> findByUserIdAndExerciseDateBetween(Long userId, java.time.LocalDateTime start, java.time.LocalDateTime end);
} 