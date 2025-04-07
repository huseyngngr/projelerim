package com.healthylife.application.service;

import com.healthylife.infrastructure.audit.AuditLog;
import com.healthylife.infrastructure.exception.ResourceNotFoundException;
import com.healthylife.infrastructure.repository.AuditLogRepository;
import com.healthylife.interfaces.rest.audit.dto.AuditLogResponse;
import com.healthylife.interfaces.rest.audit.dto.AuditLogSearchRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditQueryService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogResponse getAuditLog(UUID id) {
        return auditLogRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog", "id", id));
    }

    public Page<AuditLogResponse> searchAuditLogs(AuditLogSearchRequest request, Pageable pageable) {
        return auditLogRepository.findAll(createSpecification(request), pageable)
                .map(this::mapToResponse);
    }

    public Page<AuditLogResponse> getUserAuditLogs(UUID userId, Pageable pageable) {
        return auditLogRepository.findAll(
                (root, query, cb) -> cb.equal(root.get("userId"), userId),
                pageable
        ).map(this::mapToResponse);
    }

    public Page<AuditLogResponse> getEntityAuditLogs(String entityType, String entityId, Pageable pageable) {
        return auditLogRepository.findAll(
                (root, query, cb) -> cb.and(
                        cb.equal(root.get("entityType"), entityType),
                        cb.equal(root.get("entityId"), entityId)
                ),
                pageable
        ).map(this::mapToResponse);
    }

    private Specification<AuditLog> createSpecification(AuditLogSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getAction() != null) {
                predicates.add(cb.equal(root.get("action"), request.getAction()));
            }

            if (request.getEntityType() != null) {
                predicates.add(cb.equal(root.get("entityType"), request.getEntityType()));
            }

            if (request.getEntityId() != null) {
                predicates.add(cb.equal(root.get("entityId"), request.getEntityId()));
            }

            if (request.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), request.getUserId()));
            }

            if (request.getUserEmail() != null) {
                predicates.add(cb.equal(root.get("userEmail"), request.getUserEmail()));
            }

            if (request.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), request.getStartDate()));
            }

            if (request.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), request.getEndDate()));
            }

            if (request.getIpAddress() != null) {
                predicates.add(cb.equal(root.get("ipAddress"), request.getIpAddress()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private AuditLogResponse mapToResponse(AuditLog auditLog) {
        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .action(auditLog.getAction())
                .entityType(auditLog.getEntityType())
                .entityId(auditLog.getEntityId())
                .timestamp(auditLog.getTimestamp())
                .userId(auditLog.getUserId())
                .userEmail(auditLog.getUserEmail())
                .oldValue(auditLog.getOldValue())
                .newValue(auditLog.getNewValue())
                .ipAddress(auditLog.getIpAddress())
                .userAgent(auditLog.getUserAgent())
                .details(auditLog.getDetails())
                .build();
    }
} 