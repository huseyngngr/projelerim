package com.healthylife.infrastructure.event;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class EmailEvent implements DomainEvent {
    private String eventId;
    private LocalDateTime occurredOn;
    private String eventType;
    private String to;
    private String subject;
    private String body;
    private String template;
    private Object templateData;

    public static EmailEvent create(String to, String subject, String body) {
        return EmailEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .occurredOn(LocalDateTime.now())
                .eventType("EMAIL_SENT")
                .to(to)
                .subject(subject)
                .body(body)
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