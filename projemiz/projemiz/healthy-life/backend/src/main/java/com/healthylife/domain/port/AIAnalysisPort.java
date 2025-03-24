package com.healthylife.domain.port;

import java.util.Map;

public interface AIAnalysisPort {
    String analyzeFoodByText(String foodName, Map<String, String> nutritionalInfo);
    String analyzeFoodByImage(byte[] imageData);
    String generateHealthRecommendation(String foodAnalysis, Map<String, Object> userHealthContext);
    String generateDietaryPlan(Map<String, Object> userProfile, Map<String, Object> healthGoals);
} 