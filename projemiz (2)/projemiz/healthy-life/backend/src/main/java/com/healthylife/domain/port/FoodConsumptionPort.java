package com.healthylife.domain.port;

import com.healthylife.domain.food.FoodConsumption;
import com.healthylife.domain.food.MealType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodConsumptionPort {
    FoodConsumption save(FoodConsumption foodConsumption);
    Optional<FoodConsumption> findById(UUID id);
    List<FoodConsumption> findByHealthProfileId(UUID healthProfileId);
    List<FoodConsumption> findByHealthProfileIdAndDateRange(
        UUID healthProfileId, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
    List<FoodConsumption> findByHealthProfileIdAndMealType(
        UUID healthProfileId,
        MealType mealType
    );
    void delete(UUID id);
    boolean existsByHealthProfileIdAndId(UUID healthProfileId, UUID id);
} 