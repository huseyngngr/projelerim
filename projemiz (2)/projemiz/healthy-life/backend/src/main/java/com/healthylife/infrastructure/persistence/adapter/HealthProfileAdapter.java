package com.healthylife.infrastructure.persistence.adapter;

import com.healthylife.domain.health.HealthProfile;
import com.healthylife.domain.port.HealthProfilePort;
import com.healthylife.infrastructure.persistence.repository.HealthProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HealthProfileAdapter implements HealthProfilePort {
    private final HealthProfileRepository healthProfileRepository;

    @Override
    public HealthProfile save(HealthProfile healthProfile) {
        return healthProfileRepository.save(healthProfile);
    }

    @Override
    public Optional<HealthProfile> findById(UUID id) {
        return healthProfileRepository.findById(id);
    }

    @Override
    public List<HealthProfile> findByUserId(UUID userId) {
        return healthProfileRepository.findByUserId(userId);
    }

    @Override
    public void delete(UUID id) {
        healthProfileRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndId(UUID userId, UUID id) {
        return healthProfileRepository.existsByUserIdAndId(userId, id);
    }
} 