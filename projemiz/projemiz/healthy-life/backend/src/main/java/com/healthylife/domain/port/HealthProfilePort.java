package com.healthylife.domain.port;

import com.healthylife.domain.health.HealthProfile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HealthProfilePort {
    HealthProfile save(HealthProfile healthProfile);
    Optional<HealthProfile> findById(UUID id);
    List<HealthProfile> findByUserId(UUID userId);
    void delete(UUID id);
    boolean existsByUserIdAndId(UUID userId, UUID id);
} 