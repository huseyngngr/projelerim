package com.healthylife.infrastructure.persistence.repository;

import com.healthylife.domain.food.FoodConsumption;
import com.healthylife.domain.food.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FoodConsumptionRepository extends JpaRepository<FoodConsumption, UUID> {
    List<FoodConsumption> findByHealthProfileId(UUID healthProfileId);
    
    List<FoodConsumption> findByHealthProfileIdAndConsumedAtBetween(
        UUID healthProfileId,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
    
    List<FoodConsumption> findByHealthProfileIdAndMealType(
        UUID healthProfileId,
        MealType mealType
    );
    
    boolean existsByHealthProfileIdAndId(UUID healthProfileId, UUID id);
} 