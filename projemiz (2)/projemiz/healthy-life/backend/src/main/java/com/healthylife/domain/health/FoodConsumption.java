package com.healthylife.domain.health;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class FoodConsumption {
    private Long id;
    private Long healthProfileId;
    private String foodName;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    private LocalDateTime consumedAt;
} 