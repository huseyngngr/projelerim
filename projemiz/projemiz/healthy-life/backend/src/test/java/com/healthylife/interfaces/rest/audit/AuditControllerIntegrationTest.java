package com.healthylife.interfaces.rest.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthylife.infrastructure.audit.AuditLog;
import com.healthylife.infrastructure.repository.AuditLogRepository;
import com.healthylife.interfaces.rest.audit.dto.AuditLogSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuditControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AuditLog auditLog;
    private UUID auditLogId;

    @BeforeEach
    void setUp() {
        auditLogId = UUID.randomUUID();
        Map<String, Object> newValue = new HashMap<>();
        newValue.put("name", "John Doe");

        auditLog = AuditLog.builder()
                .id(auditLogId)
                .action("CREATE")
                .entityType("User")
                .entityId("123")
                .timestamp(LocalDateTime.now())
                .userId(UUID.randomUUID())
                .userEmail("test@example.com")
                .oldValue(new HashMap<>())
                .newValue(newValue)
                .ipAddress("127.0.0.1")
                .userAgent("Mozilla/5.0")
                .details("User created")
                .build();

        auditLogRepository.save(auditLog);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAuditLog_ShouldReturnAuditLogDetails() throws Exception {
        mockMvc.perform(get("/api/v1/audit/{id}", auditLogId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(auditLogId.toString()))
                .andExpect(jsonPath("$.action").value("CREATE"))
                .andExpect(jsonPath("$.entityType").value("User"))
                .andExpect(jsonPath("$.entityId").value("123"))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void searchAuditLogs_ShouldReturnFilteredResults() throws Exception {
        AuditLogSearchRequest searchRequest = new AuditLogSearchRequest();
        searchRequest.setAction("CREATE");
        searchRequest.setEntityType("User");

        mockMvc.perform(get("/api/v1/audit/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.content[0].action").value("CREATE"))
                .andExpect(jsonPath("$.content[0].entityType").value("User"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserAuditLogs_ShouldReturnUserSpecificLogs() throws Exception {
        mockMvc.perform(get("/api/v1/audit/user/{userId}", auditLog.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.content[0].userId").value(auditLog.getUserId().toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getEntityAuditLogs_ShouldReturnEntitySpecificLogs() throws Exception {
        mockMvc.perform(get("/api/v1/audit/entity/{entityType}/{entityId}", "User", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.content[0].entityType").value("User"))
                .andExpect(jsonPath("$.content[0].entityId").value("123"));
    }

    @Test
    void getAuditLog_WithoutAuthentication_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/audit/{id}", auditLogId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAuditLog_WithInsufficientRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/audit/{id}", auditLogId))
                .andExpect(status().isForbidden());
    }
} 