package com.healthylife.controller;

import com.healthylife.dto.NutritionDTO;
import com.healthylife.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "*")
public class NutritionController {
    @Autowired
    private NutritionService nutritionService;

    @GetMapping
    public ResponseEntity<List<NutritionDTO>> getAllNutritionRecords() {
        return ResponseEntity.ok(nutritionService.getAllNutritionRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutritionDTO> getNutritionById(@PathVariable Long id) {
        return ResponseEntity.ok(nutritionService.getNutritionById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NutritionDTO>> getNutritionByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(nutritionService.getNutritionByUserId(userId));
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<NutritionDTO>> getNutritionByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(nutritionService.getNutritionByDateRange(userId, start, end));
    }

    @GetMapping("/user/{userId}/meal-type/{mealType}")
    public ResponseEntity<List<NutritionDTO>> getNutritionByMealType(
            @PathVariable Long userId,
            @PathVariable String mealType) {
        return ResponseEntity.ok(nutritionService.getNutritionByMealType(userId, mealType));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<NutritionDTO> createNutrition(
            @PathVariable Long userId,
            @RequestBody NutritionDTO nutritionDTO) {
        return ResponseEntity.ok(nutritionService.createNutrition(userId, nutritionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutritionDTO> updateNutrition(
            @PathVariable Long id,
            @RequestBody NutritionDTO nutritionDTO) {
        return ResponseEntity.ok(nutritionService.updateNutrition(id, nutritionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutrition(@PathVariable Long id) {
        nutritionService.deleteNutrition(id);
        return ResponseEntity.ok().build();
    }
} 