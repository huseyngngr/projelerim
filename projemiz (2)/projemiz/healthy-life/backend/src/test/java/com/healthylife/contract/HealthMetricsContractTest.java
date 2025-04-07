package com.healthylife.contract;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.healthylife.domain.model.HealthMetric;
import com.healthylife.domain.service.HealthMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("healthylife-backend")
@PactFolder("pacts")
@ActiveProfiles("test")
public class HealthMetricsContractTest {

    @LocalServerPort
    private int port;

    @MockBean
    private HealthMetricService healthMetricService;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("user has health metrics")
    public void toUserHasHealthMetricsState() {
        HealthMetric metric1 = HealthMetric.builder()
                .id(1L)
                .userId("user123")
                .type("WEIGHT")
                .value(75.5)
                .unit("kg")
                .timestamp(LocalDateTime.now())
                .build();

        HealthMetric metric2 = HealthMetric.builder()
                .id(2L)
                .userId("user123")
                .type("BLOOD_PRESSURE")
                .value(120.0)
                .unit("mmHg")
                .timestamp(LocalDateTime.now())
                .build();

        when(healthMetricService.getUserMetrics("user123"))
                .thenReturn(Arrays.asList(metric1, metric2));
    }

    @State("health metric exists")
    public void toHealthMetricExistsState() {
        HealthMetric metric = HealthMetric.builder()
                .id(1L)
                .userId("user123")
                .type("WEIGHT")
                .value(75.5)
                .unit("kg")
                .timestamp(LocalDateTime.now())
                .build();

        when(healthMetricService.getMetric(1L))
                .thenReturn(metric);
    }

    @State("health metric does not exist")
    public void toHealthMetricDoesNotExistState() {
        when(healthMetricService.getMetric(999L))
                .thenReturn(null);
    }
} 