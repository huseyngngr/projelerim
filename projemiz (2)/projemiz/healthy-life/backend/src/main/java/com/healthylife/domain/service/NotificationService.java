package com.healthylife.domain.service;

import com.healthylife.domain.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Notification> createNotification(Notification notification);
    Mono<Notification> getNotificationById(Long id);
    Flux<Notification> getNotificationsByRecipient(String recipient);
    Mono<Notification> markAsRead(Long id);
    Mono<Void> deleteNotification(Long id);
} 