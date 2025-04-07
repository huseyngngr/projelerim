package com.healthylife.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Exercise type is required")
    @Size(max = 50, message = "Exercise type cannot exceed 50 characters")
    private String exerciseType;

    @NotNull(message = "Duration is required")
    @Min(value = 0, message = "Duration must be greater than or equal to 0")
    private Integer duration;

    @Min(value = 0, message = "Calories burned must be greater than or equal to 0")
    private Integer caloriesBurned;

    @DecimalMin(value = "0.0", message = "Distance must be greater than or equal to 0")
    private Double distance;

    @Min(value = 0, message = "Heart rate must be greater than or equal to 0")
    @Max(value = 250, message = "Heart rate must be less than or equal to 250")
    private Integer heartRate;

    @Size(max = 10, message = "Intensity cannot exceed 10 characters")
    private String intensity;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @NotNull(message = "Exercise date is required")
    private LocalDateTime exerciseDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 