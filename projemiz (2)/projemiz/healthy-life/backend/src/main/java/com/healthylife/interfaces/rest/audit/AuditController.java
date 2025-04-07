package com.healthylife.interfaces.rest.audit;

import com.healthylife.application.service.AuditQueryService;
import com.healthylife.interfaces.rest.audit.dto.AuditLogResponse;
import com.healthylife.interfaces.rest.audit.dto.AuditLogSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@Tag(name = "Audit", description = "Audit log yönetimi API'leri")
@SecurityRequirement(name = "bearerAuth")
public class AuditController {
    private final AuditQueryService auditQueryService;

    @Operation(summary = "Audit log detaylarını getirir")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditLogResponse> getAuditLog(
            @Parameter(description = "Audit log ID'si")
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(auditQueryService.getAuditLog(id));
    }

    @Operation(summary = "Audit logları arar")
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditLogResponse>> searchAuditLogs(
            @Parameter(description = "Arama kriterleri")
            AuditLogSearchRequest searchRequest,
            @Parameter(description = "Sayfalama bilgisi")
            Pageable pageable
    ) {
        return ResponseEntity.ok(auditQueryService.searchAuditLogs(searchRequest, pageable));
    }

    @Operation(summary = "Belirli bir kullanıcının audit loglarını getirir")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditLogResponse>> getUserAuditLogs(
            @Parameter(description = "Kullanıcı ID'si")
            @PathVariable UUID userId,
            @Parameter(description = "Sayfalama bilgisi")
            Pageable pageable
    ) {
        return ResponseEntity.ok(auditQueryService.getUserAuditLogs(userId, pageable));
    }

    @Operation(summary = "Belirli bir varlığın audit loglarını getirir")
    @GetMapping("/entity/{entityType}/{entityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditLogResponse>> getEntityAuditLogs(
            @Parameter(description = "Varlık tipi")
            @PathVariable String entityType,
            @Parameter(description = "Varlık ID'si")
            @PathVariable String entityId,
            @Parameter(description = "Sayfalama bilgisi")
            Pageable pageable
    ) {
        return ResponseEntity.ok(auditQueryService.getEntityAuditLogs(entityType, entityId, pageable));
    }
} 