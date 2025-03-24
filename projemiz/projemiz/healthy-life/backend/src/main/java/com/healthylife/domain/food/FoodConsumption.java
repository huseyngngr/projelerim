package com.healthylife.domain.food;

import com.healthylife.domain.common.BaseEntity;
import com.healthylife.domain.health.HealthProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_consumptions")
@Getter
@Setter
@NoArgsConstructor
public class FoodConsumption extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;

    @Column(nullable = false)
    private String foodName;

    private double portion; // gram
    private String portionUnit;

    @Column(columnDefinition = "text")
    private String nutritionalInfo;

    @Column(columnDefinition = "text")
    private String aiAnalysis;

    @Column(columnDefinition = "text")
    private String aiRecommendation;

    private LocalDateTime consumedAt;

    @Column(length = 1000)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column(columnDefinition = "text")
    private String userNotes;
} 