package com.healthylife.infrastructure.persistence.adapter;

import com.healthylife.domain.food.FoodConsumption;
import com.healthylife.domain.food.MealType;
import com.healthylife.domain.port.FoodConsumptionPort;
import com.healthylife.infrastructure.persistence.repository.FoodConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FoodConsumptionAdapter implements FoodConsumptionPort {
    private final FoodConsumptionRepository foodConsumptionRepository;

    @Override
    public FoodConsumption save(FoodConsumption foodConsumption) {
        return foodConsumptionRepository.save(foodConsumption);
    }

    @Override
    public Optional<FoodConsumption> findById(UUID id) {
        return foodConsumptionRepository.findById(id);
    }

    @Override
    public List<FoodConsumption> findByHealthProfileId(UUID healthProfileId) {
        return foodConsumptionRepository.findByHealthProfileId(healthProfileId);
    }

    @Override
    public List<FoodConsumption> findByHealthProfileIdAndDateRange(
        UUID healthProfileId,
        LocalDateTime startDate,
        LocalDateTime endDate
    ) {
        return foodConsumptionRepository.findByHealthProfileIdAndConsumedAtBetween(
            healthProfileId,
            startDate,
            endDate
        );
    }

    @Override
    public List<FoodConsumption> findByHealthProfileIdAndMealType(
        UUID healthProfileId,
        MealType mealType
    ) {
        return foodConsumptionRepository.findByHealthProfileIdAndMealType(
            healthProfileId,
            mealType
        );
    }

    @Override
    public void delete(UUID id) {
        foodConsumptionRepository.deleteById(id);
    }

    @Override
    public boolean existsByHealthProfileIdAndId(UUID healthProfileId, UUID id) {
        return foodConsumptionRepository.existsByHealthProfileIdAndId(healthProfileId, id);
    }
} 