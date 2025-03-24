package com.healthylife.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishEvent(DomainEvent event) {
        publisher.publishEvent(event);
    }
}

public interface DomainEvent {
    String getEventType();
    String getAggregateId();
    long getTimestamp();
} 