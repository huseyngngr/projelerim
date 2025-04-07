package com.healthylife.infrastructure.event;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReportEvent implements DomainEvent {
    private String eventId;
    private LocalDateTime occurredOn;
    private String eventType;
    private String reportType;
    private String userId;
    private String reportData;
    private String status;

    public static ReportEvent create(String reportType, String userId, String reportData) {
        return ReportEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .occurredOn(LocalDateTime.now())
                .eventType("REPORT_GENERATED")
                .reportType(reportType)
                .userId(userId)
                .reportData(reportData)
                .status("PENDING")
                .build();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String getEventType() {
        return eventType;
    }
} 