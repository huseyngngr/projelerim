package com.healthylife.interfaces.rest.audit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Audit log arama kriterleri")
public class AuditLogSearchRequest {
    @Schema(description = "İşlem tipi")
    private String action;

    @Schema(description = "Varlık tipi")
    private String entityType;

    @Schema(description = "Varlık ID'si")
    private String entityId;

    @Schema(description = "İşlemi yapan kullanıcı ID'si")
    private UUID userId;

    @Schema(description = "İşlemi yapan kullanıcı email'i")
    private String userEmail;

    @Schema(description = "Başlangıç tarihi")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Schema(description = "Bitiş tarihi")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Schema(description = "IP adresi")
    private String ipAddress;
} 