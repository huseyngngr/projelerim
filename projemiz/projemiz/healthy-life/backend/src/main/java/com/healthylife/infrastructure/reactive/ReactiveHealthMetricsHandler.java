package com.healthylife.infrastructure.reactive;

import com.healthylife.domain.model.HealthMetric;
import com.healthylife.domain.service.HealthMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ReactiveHealthMetricsHandler {

    private final HealthMetricService healthMetricService;

    public Mono<ServerResponse> streamMetrics(ServerRequest request) {
        Flux<HealthMetric> metricsFlux = Flux.interval(Duration.ofSeconds(1))
                .flatMap(sequence -> Flux.fromIterable(healthMetricService.getLatestMetrics()))
                .share();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(metricsFlux, HealthMetric.class);
    }

    public Mono<ServerResponse> streamUserMetrics(ServerRequest request) {
        String userId = request.pathVariable("userId");
        
        Flux<HealthMetric> userMetricsFlux = Flux.interval(Duration.ofSeconds(1))
                .flatMap(sequence -> Flux.fromIterable(healthMetricService.getUserMetrics(userId)))
                .share();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userMetricsFlux, HealthMetric.class);
    }
} 