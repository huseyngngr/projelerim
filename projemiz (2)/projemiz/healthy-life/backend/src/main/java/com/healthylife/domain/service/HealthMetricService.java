package com.healthylife.domain.service;

import com.healthylife.domain.health.HealthProfile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HealthMetricService {
    Mono<HealthProfile> createHealthProfile(HealthProfile healthProfile);
    Mono<HealthProfile> getHealthProfileById(Long id);
    Mono<HealthProfile> getHealthProfileByUserId(Long userId);
    Mono<HealthProfile> updateHealthProfile(HealthProfile healthProfile);
    Mono<Void> deleteHealthProfile(Long id);
    Flux<HealthProfile> getAllHealthProfiles();
} 