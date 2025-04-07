package com.healthylife.interfaces.rest.audit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Audit log detayları")
public class AuditLogResponse {
    @Schema(description = "Audit log ID'si")
    private UUID id;

    @Schema(description = "Gerçekleştirilen işlem")
    private String action;

    @Schema(description = "İşlemin yapıldığı varlık tipi")
    private String entityType;

    @Schema(description = "İşlemin yapıldığı varlık ID'si")
    private String entityId;

    @Schema(description = "İşlem zamanı")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "İşlemi yapan kullanıcı ID'si")
    private UUID userId;

    @Schema(description = "İşlemi yapan kullanıcı email'i")
    private String userEmail;

    @Schema(description = "İşlem öncesi değerler")
    private Map<String, Object> oldValue;

    @Schema(description = "İşlem sonrası değerler")
    private Map<String, Object> newValue;

    @Schema(description = "İşlemin yapıldığı IP adresi")
    private String ipAddress;

    @Schema(description = "Kullanılan tarayıcı/uygulama bilgisi")
    private String userAgent;

    @Schema(description = "İşlem detayları")
    private String details;
} 