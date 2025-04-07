package com.healthylife.mapper;

import com.healthylife.dto.NutritionDTO;
import com.healthylife.model.Nutrition;
import org.springframework.stereotype.Component;

@Component
public class NutritionMapper {

    public NutritionDTO toDTO(Nutrition nutrition) {
        NutritionDTO dto = new NutritionDTO();
        dto.setId(nutrition.getId());
        dto.setUserId(nutrition.getUser().getId());
        dto.setMealType(nutrition.getMealType());
        dto.setFoodName(nutrition.getFoodName());
        dto.setCalories(nutrition.getCalories());
        dto.setProtein(nutrition.getProtein());
        dto.setCarbohydrates(nutrition.getCarbohydrates());
        dto.setFat(nutrition.getFat());
        dto.setNotes(nutrition.getNotes());
        dto.setMealDate(nutrition.getMealDate());
        dto.setCreatedAt(nutrition.getCreatedAt());
        dto.setUpdatedAt(nutrition.getUpdatedAt());
        return dto;
    }

    public Nutrition toEntity(NutritionDTO dto) {
        Nutrition nutrition = new Nutrition();
        nutrition.setId(dto.getId());
        nutrition.setMealType(dto.getMealType());
        nutrition.setFoodName(dto.getFoodName());
        nutrition.setCalories(dto.getCalories());
        nutrition.setProtein(dto.getProtein());
        nutrition.setCarbohydrates(dto.getCarbohydrates());
        nutrition.setFat(dto.getFat());
        nutrition.setNotes(dto.getNotes());
        nutrition.setMealDate(dto.getMealDate());
        return nutrition;
    }
} 