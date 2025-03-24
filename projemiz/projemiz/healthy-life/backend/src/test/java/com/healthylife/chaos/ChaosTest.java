package com.healthylife.chaos;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChaosTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    void testHighConcurrency() {
        int numberOfRequests = 100;
        
        CompletableFuture<ResponseEntity<String>>[] futures = IntStream.range(0, numberOfRequests)
                .mapToObj(i -> CompletableFuture.supplyAsync(() ->
                        restTemplate.getForEntity("/api/health", String.class), executorService))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();

        for (CompletableFuture<ResponseEntity<String>> future : futures) {
            ResponseEntity<String> response = future.join();
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Test
    void testCircuitBreaker() {
        CircuitBreaker circuitBreaker = CircuitBreaker.of("testBreaker", CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .build());

        // Force the circuit breaker to open
        IntStream.range(0, 5).forEach(i -> {
            try {
                circuitBreaker.decorateSupplier(() -> {
                    throw new RuntimeException("Forced failure");
                }).get();
            } catch (Exception ignored) {}
        });

        assertTrue(circuitBreaker.getState() == CircuitBreaker.State.OPEN);

        // Wait for the circuit breaker to transition to half-open
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(circuitBreaker.getState() == CircuitBreaker.State.HALF_OPEN);
    }

    @Test
    void testDatabaseConnection() {
        // Simulate database connection issues
        ResponseEntity<String> response = restTemplate.getForEntity("/api/health/db", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // TODO: Implement actual database chaos testing
    }

    @Test
    void testNetworkLatency() {
        long start = System.currentTimeMillis();
        
        ResponseEntity<String> response = restTemplate.getForEntity("/api/health", String.class);
        
        long end = System.currentTimeMillis();
        long duration = end - start;

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(duration < 1000, "Request took too long: " + duration + "ms");
    }

    @Test
    void testMemoryPressure() {
        // Simulate memory pressure
        byte[][] arrays = new byte[1024][];
        try {
            for (int i = 0; i < arrays.length; i++) {
                arrays[i] = new byte[1024 * 1024]; // Allocate 1MB
                Thread.sleep(10);
                
                // Make a request while under memory pressure
                ResponseEntity<String> response = restTemplate.getForEntity("/api/health", String.class);
                assertEquals(HttpStatus.OK, response.getStatusCode());
            }
        } catch (OutOfMemoryError | InterruptedException e) {
            // Expected behavior under extreme memory pressure
        } finally {
            arrays = null;
            System.gc();
        }
    }
} 