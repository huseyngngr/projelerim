package com.healthylife.infrastructure.reactive;

import com.healthylife.domain.model.Notification;
import com.healthylife.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ReactiveNotificationHandler {

    private final NotificationService notificationService;
    private Sinks.Many<Notification> notificationSink;

    @PostConstruct
    public void init() {
        notificationSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Mono<ServerResponse> streamNotifications(ServerRequest request) {
        Flux<Notification> notificationFlux = notificationSink.asFlux()
                .share();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(notificationFlux, Notification.class);
    }

    public Mono<ServerResponse> streamUserNotifications(ServerRequest request) {
        String userId = request.pathVariable("userId");
        
        Flux<Notification> userNotificationFlux = notificationSink.asFlux()
                .filter(notification -> notification.getUserId().equals(userId))
                .share();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userNotificationFlux, Notification.class);
    }

    public void pushNotification(Notification notification) {
        notificationSink.tryEmitNext(notification);
    }
} 