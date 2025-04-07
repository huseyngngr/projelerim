package com.healthylife.service;

import com.healthylife.dto.NutritionDTO;
import com.healthylife.model.Nutrition;
import com.healthylife.model.User;
import com.healthylife.repository.NutritionRepository;
import com.healthylife.repository.UserRepository;
import com.healthylife.mapper.NutritionMapper;
import com.healthylife.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NutritionService {
    @Autowired
    private NutritionRepository nutritionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NutritionMapper nutritionMapper;

    @Transactional(readOnly = true)
    public List<NutritionDTO> getAllNutritionRecords() {
        return nutritionRepository.findAll().stream()
                .map(nutritionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NutritionDTO getNutritionById(Long id) {
        Nutrition nutrition = nutritionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nutrition record not found with id: " + id));
        return nutritionMapper.toDTO(nutrition);
    }

    @Transactional(readOnly = true)
    public List<NutritionDTO> getNutritionByUserId(Long userId) {
        return nutritionRepository.findByUserIdOrderByMealDateDesc(userId).stream()
                .map(nutritionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public NutritionDTO createNutrition(Long userId, NutritionDTO nutritionDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Nutrition nutrition = nutritionMapper.toEntity(nutritionDTO);
        nutrition.setUser(user);

        Nutrition savedNutrition = nutritionRepository.save(nutrition);
        return nutritionMapper.toDTO(savedNutrition);
    }

    @Transactional
    public NutritionDTO updateNutrition(Long userId, Long nutritionId, NutritionDTO nutritionDTO) {
        Nutrition nutrition = nutritionRepository.findByIdAndUserId(nutritionId, userId)
            .orElseThrow(() -> new RuntimeException("Nutrition not found"));

        nutrition.setMealType(nutritionDTO.getMealType());
        nutrition.setFoodName(nutritionDTO.getFoodName());
        nutrition.setCalories(nutritionDTO.getCalories());
        nutrition.setProtein(nutritionDTO.getProtein());
        nutrition.setCarbohydrates(nutritionDTO.getCarbohydrates());
        nutrition.setFat(nutritionDTO.getFat());
        nutrition.setNotes(nutritionDTO.getNotes());
        nutrition.setMealDate(nutritionDTO.getMealDate());

        Nutrition updatedNutrition = nutritionRepository.save(nutrition);
        return nutritionMapper.toDTO(updatedNutrition);
    }

    @Transactional
    public void deleteNutrition(Long userId, Long nutritionId) {
        Nutrition nutrition = nutritionRepository.findByIdAndUserId(nutritionId, userId)
            .orElseThrow(() -> new RuntimeException("Nutrition not found"));
        nutritionRepository.delete(nutrition);
    }

    @Transactional(readOnly = true)
    public List<NutritionDTO> getNutritionByDateRange(Long userId, LocalDateTime start, LocalDateTime end) {
        return nutritionRepository.findByUserIdAndMealDateBetween(userId, start, end).stream()
                .map(nutritionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NutritionDTO> getNutritionByMealType(Long userId, String mealType) {
        return nutritionRepository.findByUserIdAndMealType(userId, mealType).stream()
                .map(nutritionMapper::toDTO)
                .collect(Collectors.toList());
    }
} 