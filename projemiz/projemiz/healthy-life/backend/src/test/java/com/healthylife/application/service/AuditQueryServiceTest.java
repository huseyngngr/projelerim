package com.healthylife.application.service;

import com.healthylife.infrastructure.audit.AuditLog;
import com.healthylife.infrastructure.exception.ResourceNotFoundException;
import com.healthylife.infrastructure.repository.AuditLogRepository;
import com.healthylife.interfaces.rest.audit.dto.AuditLogResponse;
import com.healthylife.interfaces.rest.audit.dto.AuditLogSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditQueryServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditQueryService auditQueryService;

    private AuditLog auditLog;
    private UUID auditLogId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        auditLogId = UUID.randomUUID();
        now = LocalDateTime.now();
        
        auditLog = AuditLog.builder()
                .id(auditLogId)
                .action("CREATE")
                .entityType("User")
                .entityId("123")
                .timestamp(now)
                .userId(UUID.randomUUID())
                .userEmail("test@example.com")
                .oldValue(new HashMap<>())
                .newValue(Map.of("name", "John Doe"))
                .ipAddress("127.0.0.1")
                .userAgent("Mozilla/5.0")
                .details("User created")
                .build();
    }

    @Test
    void getAuditLog_WhenExists_ShouldReturnAuditLogResponse() {
        when(auditLogRepository.findById(auditLogId)).thenReturn(Optional.of(auditLog));

        AuditLogResponse response = auditQueryService.getAuditLog(auditLogId);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(auditLogId);
        assertThat(response.getAction()).isEqualTo("CREATE");
        assertThat(response.getEntityType()).isEqualTo("User");
        assertThat(response.getTimestamp()).isEqualTo(now);
    }

    @Test
    void getAuditLog_WhenNotExists_ShouldThrowResourceNotFoundException() {
        when(auditLogRepository.findById(auditLogId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> auditQueryService.getAuditLog(auditLogId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("AuditLog")
                .hasMessageContaining(auditLogId.toString());
    }

    @Test
    void searchAuditLogs_ShouldReturnPageOfAuditLogResponses() {
        AuditLogSearchRequest searchRequest = new AuditLogSearchRequest();
        searchRequest.setAction("CREATE");
        searchRequest.setEntityType("User");

        Pageable pageable = PageRequest.of(0, 10);
        Page<AuditLog> auditLogPage = new PageImpl<>(List.of(auditLog));

        when(auditLogRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(auditLogPage);

        Page<AuditLogResponse> response = auditQueryService.searchAuditLogs(searchRequest, pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getAction()).isEqualTo("CREATE");
    }

    @Test
    void getUserAuditLogs_ShouldReturnUserSpecificLogs() {
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<AuditLog> auditLogPage = new PageImpl<>(List.of(auditLog));

        when(auditLogRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(auditLogPage);

        Page<AuditLogResponse> response = auditQueryService.getUserAuditLogs(userId, pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
    }

    @Test
    void getEntityAuditLogs_ShouldReturnEntitySpecificLogs() {
        String entityType = "User";
        String entityId = "123";
        Pageable pageable = PageRequest.of(0, 10);
        Page<AuditLog> auditLogPage = new PageImpl<>(List.of(auditLog));

        when(auditLogRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(auditLogPage);

        Page<AuditLogResponse> response = auditQueryService.getEntityAuditLogs(entityType, entityId, pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getEntityType()).isEqualTo(entityType);
        assertThat(response.getContent().get(0).getEntityId()).isEqualTo(entityId);
    }
} 