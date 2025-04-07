package com.healthylife.infrastructure.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry registry;
    private final Counter loginAttempts;
    private final Counter failedLogins;
    private final Timer apiRequestTimer;

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;
        this.loginAttempts = Counter.builder("app.login.attempts")
                .description("Total number of login attempts")
                .register(registry);
        this.failedLogins = Counter.builder("app.login.failed")
                .description("Number of failed login attempts")
                .register(registry);
        this.apiRequestTimer = Timer.builder("app.request.duration")
                .description("API request duration")
                .register(registry);
    }

    public void recordLoginAttempt() {
        loginAttempts.increment();
    }

    public void recordFailedLogin() {
        failedLogins.increment();
    }

    public void recordRequestDuration(long durationMs) {
        apiRequestTimer.record(durationMs, TimeUnit.MILLISECONDS);
    }

    public void recordCustomMetric(String name, double value, String... tags) {
        registry.gauge(name, value);
    }

    public void incrementCustomCounter(String name, String... tags) {
        Counter counter = Counter.builder(name)
                .tags(tags)
                .register(registry);
        counter.increment();
    }
} 