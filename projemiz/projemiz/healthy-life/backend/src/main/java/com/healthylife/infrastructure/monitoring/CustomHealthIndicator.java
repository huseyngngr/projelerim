package com.healthylife.infrastructure.monitoring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up()
                        .withDetail("database", "Database connection is healthy")
                        .withDetail("maxPoolSize", connection.getMetaData().getMaxConnections())
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "Database connection is not valid")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "Database connection failed")
                    .withException(e)
                    .build();
        }
    }
} 