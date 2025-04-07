package com.healthylife.infrastructure.audit;

import com.healthylife.application.service.AuditService;
import com.healthylife.infrastructure.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AuditServiceIntegrationTest {

    @Autowired
    private AuditService auditService;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Test
    void logAction_ShouldCreateAuditLogEntry() {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("User-Agent", "Test User Agent");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String action = "TEST_ACTION";
        String entityType = "TEST_ENTITY";
        String entityId = UUID.randomUUID().toString();
        Map<String, Object> oldValue = new HashMap<>();
        Map<String, Object> newValue = new HashMap<>();
        newValue.put("field1", "value1");
        String details = "Test audit log";

        // Act
        auditService.logAction(action, entityType, entityId, oldValue, newValue, details);

        // Assert
        Optional<AuditLog> savedLog = auditLogRepository.findAll().stream()
                .filter(log -> log.getAction().equals(action) && log.getEntityId().equals(entityId))
                .findFirst();

        assertThat(savedLog).isPresent();
        AuditLog auditLog = savedLog.get();
        assertThat(auditLog.getAction()).isEqualTo(action);
        assertThat(auditLog.getEntityType()).isEqualTo(entityType);
        assertThat(auditLog.getEntityId()).isEqualTo(entityId);
        assertThat(auditLog.getOldValue()).isEqualTo(oldValue);
        assertThat(auditLog.getNewValue()).isEqualTo(newValue);
        assertThat(auditLog.getDetails()).isEqualTo(details);
        assertThat(auditLog.getIpAddress()).isEqualTo("127.0.0.1");
        assertThat(auditLog.getUserAgent()).isEqualTo("Test User Agent");
    }

    @Test
    void logAction_WithNullValues_ShouldCreateAuditLogEntry() {
        // Act
        auditService.logAction("TEST_ACTION", "TEST_ENTITY", null, null, null, null);

        // Assert
        Optional<AuditLog> savedLog = auditLogRepository.findAll().stream()
                .filter(log -> log.getAction().equals("TEST_ACTION"))
                .findFirst();

        assertThat(savedLog).isPresent();
        AuditLog auditLog = savedLog.get();
        assertThat(auditLog.getEntityId()).isNull();
        assertThat(auditLog.getOldValue()).isNull();
        assertThat(auditLog.getNewValue()).isNull();
        assertThat(auditLog.getDetails()).isNull();
    }

    @Test
    void logAction_WithLargeValues_ShouldCreateAuditLogEntry() {
        // Arrange
        Map<String, Object> largeValue = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            largeValue.put("key" + i, "value" + i);
        }

        // Act
        auditService.logAction("TEST_ACTION", "TEST_ENTITY", "123", largeValue, largeValue, "Large test");

        // Assert
        Optional<AuditLog> savedLog = auditLogRepository.findAll().stream()
                .filter(log -> log.getAction().equals("TEST_ACTION"))
                .findFirst();

        assertThat(savedLog).isPresent();
        AuditLog auditLog = savedLog.get();
        assertThat(auditLog.getOldValue()).hasSize(100);
        assertThat(auditLog.getNewValue()).hasSize(100);
    }
} 