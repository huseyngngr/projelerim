package com.healthylife.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class Notification {
    private Long id;
    private String title;
    private String message;
    private String type;
    private String recipient;
    private LocalDateTime createdAt;
    private boolean read;
} 