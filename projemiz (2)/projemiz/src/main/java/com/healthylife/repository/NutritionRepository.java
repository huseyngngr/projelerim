package com.healthylife.repository;

import com.healthylife.model.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    List<Nutrition> findByUserId(Long userId);
    Optional<Nutrition> findByIdAndUserId(Long id, Long userId);
    List<Nutrition> findByUserIdOrderByMealDateDesc(Long userId);
    List<Nutrition> findByUserIdAndMealDateBetween(Long userId, java.time.LocalDateTime start, java.time.LocalDateTime end);
    List<Nutrition> findByUserIdAndMealType(Long userId, String mealType);
    void deleteByIdAndUserId(Long id, Long userId);
} 