package com.healthylife.domain.health;

import com.healthylife.domain.BaseEntity;
import com.healthylife.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "health_profiles")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class HealthProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String gender;

    @Column
    private String medicalConditions;

    @Column
    private String allergies;

    @Column
    private String medications;

    @Column
    private String dietaryRestrictions;

    @Column
    private String fitnessGoals;

    @Column
    private String notes;

    @OneToMany(mappedBy = "healthProfile", cascade = CascadeType.ALL)
    private List<FoodConsumption> foodConsumptionHistory;

    private String activityLevel;
    private LocalDateTime lastUpdated;
} 