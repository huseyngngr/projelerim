package com.healthylife.domain.health;

import com.healthylife.domain.common.BaseEntity;
import com.healthylife.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "health_profiles")
@Getter
@Setter
@NoArgsConstructor
public class HealthProfile extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double height; // cm
    private double weight; // kg
    private LocalDate birthDate;
    private String gender;

    @ElementCollection
    @CollectionTable(name = "dietary_restrictions")
    private List<String> dietaryRestrictions;

    @ElementCollection
    @CollectionTable(name = "health_goals")
    private List<String> healthGoals;

    @Column(columnDefinition = "text")
    private String medicalConditions;

    @Column(columnDefinition = "text")
    private String allergies;

    @OneToMany(mappedBy = "healthProfile", cascade = CascadeType.ALL)
    private List<FoodConsumption> foodConsumptionHistory;
} 