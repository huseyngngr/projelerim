package com.healthylife.infrastructure.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class DomainEventListener {

    @Async("taskExecutor")
    @EventListener
    public void handleDomainEvent(DomainEvent event) {
        log.info("Processing domain event: {}", event.getEventType());
        // Event işleme mantığı
    }

    @Async("emailExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEmailEvent(EmailEvent event) {
        log.info("Processing email event for user: {}", event.getUserId());
        // Email gönderme mantığı
    }

    @Async("reportExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReportEvent(ReportEvent event) {
        log.info("Generating report: {}", event.getReportType());
        // Rapor oluşturma mantığı
    }
}

public class EmailEvent implements DomainEvent {
    private final String userId;
    private final String emailType;
    private final long timestamp;

    public EmailEvent(String userId, String emailType) {
        this.userId = userId;
        this.emailType = emailType;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String getEventType() {
        return "EMAIL_EVENT";
    }

    @Override
    public String getAggregateId() {
        return userId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmailType() {
        return emailType;
    }
}

public class ReportEvent implements DomainEvent {
    private final String reportId;
    private final String reportType;
    private final long timestamp;

    public ReportEvent(String reportId, String reportType) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String getEventType() {
        return "REPORT_EVENT";
    }

    @Override
    public String getAggregateId() {
        return reportId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public String getReportType() {
        return reportType;
    }
} 