package com.healthylife.infrastructure.persistence.repository;

import com.healthylife.domain.health.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HealthProfileRepository extends JpaRepository<HealthProfile, UUID> {
    List<HealthProfile> findByUserId(UUID userId);
    boolean existsByUserIdAndId(UUID userId, UUID id);
} 