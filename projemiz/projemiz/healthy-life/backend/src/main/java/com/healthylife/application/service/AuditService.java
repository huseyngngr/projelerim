package com.healthylife.application.service;

import com.healthylife.infrastructure.audit.AuditLog;
import com.healthylife.infrastructure.repository.AuditLogRepository;
import com.healthylife.infrastructure.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final HttpServletRequest request;

    @Transactional
    public void logAction(String action, 
                         String entityType, 
                         String entityId, 
                         Map<String, Object> oldValue, 
                         Map<String, Object> newValue, 
                         String details) {
        var currentUser = SecurityUtils.getCurrentUser();
        
        var auditLog = AuditLog.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .timestamp(LocalDateTime.now())
                .userId(currentUser.map(user -> UUID.fromString(user.getId())).orElse(null))
                .userEmail(currentUser.map(user -> user.getEmail()).orElse("SYSTEM"))
                .oldValue(oldValue)
                .newValue(newValue)
                .ipAddress(getClientIp())
                .userAgent(request.getHeader("User-Agent"))
                .details(details)
                .build();

        auditLogRepository.save(auditLog);
    }

    private String getClientIp() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
} 