package com.healthylife.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Meal type is required")
    @Size(max = 20, message = "Meal type cannot exceed 20 characters")
    private String mealType;

    @NotBlank(message = "Food name is required")
    @Size(max = 100, message = "Food name cannot exceed 100 characters")
    private String foodName;

    @NotNull(message = "Calories is required")
    @DecimalMin(value = "0.0", message = "Calories must be greater than or equal to 0")
    @Digits(integer = 5, fraction = 2, message = "Calories must have at most 5 digits in total, with 2 decimal places")
    private Double calories;

    @DecimalMin(value = "0.0", message = "Protein must be greater than or equal to 0")
    @Digits(integer = 5, fraction = 2, message = "Protein must have at most 5 digits in total, with 2 decimal places")
    private Double protein;

    @DecimalMin(value = "0.0", message = "Carbohydrates must be greater than or equal to 0")
    @Digits(integer = 5, fraction = 2, message = "Carbohydrates must have at most 5 digits in total, with 2 decimal places")
    private Double carbohydrates;

    @DecimalMin(value = "0.0", message = "Fat must be greater than or equal to 0")
    @Digits(integer = 5, fraction = 2, message = "Fat must have at most 5 digits in total, with 2 decimal places")
    private Double fat;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @NotNull(message = "Meal date is required")
    private LocalDateTime mealDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 