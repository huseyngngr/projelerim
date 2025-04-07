package com.healthylife.e2e;

import com.healthylife.domain.model.HealthMetric;
import com.healthylife.domain.model.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HealthMetricsE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setupTestData() {
        // Clean up existing data
        jdbcTemplate.execute("DELETE FROM health_metrics");
        jdbcTemplate.execute("DELETE FROM users");

        // Insert test user
        jdbcTemplate.update(
            "INSERT INTO users (id, email, password, name) VALUES (?, ?, ?, ?)",
            "test-user", "test@example.com", "hashedPassword", "Test User"
        );
    }

    @Test
    void testCompleteHealthMetricsFlow() {
        // 1. Login
        driver.get("http://localhost:" + port + "/login");
        
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        emailInput.sendKeys("test@example.com");
        
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("password123");
        
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // 2. Navigate to health metrics page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dashboard")));
        driver.get("http://localhost:" + port + "/health-metrics");

        // 3. Add new health metric
        WebElement addMetricButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-metric-button")));
        addMetricButton.click();

        WebElement metricTypeSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("metric-type")));
        metricTypeSelect.sendKeys("WEIGHT");

        WebElement metricValueInput = driver.findElement(By.id("metric-value"));
        metricValueInput.sendKeys("75.5");

        WebElement metricUnitInput = driver.findElement(By.id("metric-unit"));
        metricUnitInput.sendKeys("kg");

        WebElement saveButton = driver.findElement(By.id("save-metric-button"));
        saveButton.click();

        // 4. Verify metric was added
        WebElement metricsList = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("metrics-list")));
        assertTrue(metricsList.getText().contains("75.5 kg"));

        // 5. Edit metric
        WebElement editButton = driver.findElement(By.cssSelector("[data-testid='edit-metric-button']"));
        editButton.click();

        WebElement editValueInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("edit-metric-value")));
        editValueInput.clear();
        editValueInput.sendKeys("76.0");

        WebElement updateButton = driver.findElement(By.id("update-metric-button"));
        updateButton.click();

        // 6. Verify metric was updated
        wait.until(ExpectedConditions.textToBePresentInElement(metricsList, "76.0 kg"));

        // 7. Delete metric
        WebElement deleteButton = driver.findElement(By.cssSelector("[data-testid='delete-metric-button']"));
        deleteButton.click();

        WebElement confirmDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("confirm-delete-button")));
        confirmDeleteButton.click();

        // 8. Verify metric was deleted
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.id("metrics-list"), "76.0 kg"));
    }

    @Test
    void testHealthMetricsVisualization() {
        // Add test data
        jdbcTemplate.update(
            "INSERT INTO health_metrics (user_id, type, value, unit, timestamp) VALUES (?, ?, ?, ?, ?)",
            "test-user", "WEIGHT", 75.5, "kg", "2024-01-01"
        );
        jdbcTemplate.update(
            "INSERT INTO health_metrics (user_id, type, value, unit, timestamp) VALUES (?, ?, ?, ?, ?)",
            "test-user", "WEIGHT", 75.0, "kg", "2024-01-02"
        );

        // Navigate to visualization page
        driver.get("http://localhost:" + port + "/health-metrics/visualization");

        // Verify chart is displayed
        WebElement chart = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("metrics-chart")));
        assertTrue(chart.isDisplayed());

        // Verify data points
        assertTrue(driver.getPageSource().contains("75.5"));
        assertTrue(driver.getPageSource().contains("75.0"));
    }

    @Test
    void testErrorHandling() {
        driver.get("http://localhost:" + port + "/health-metrics");

        // Try to add invalid metric
        WebElement addMetricButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-metric-button")));
        addMetricButton.click();

        WebElement metricValueInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("metric-value")));
        metricValueInput.sendKeys("-1"); // Invalid negative value

        WebElement saveButton = driver.findElement(By.id("save-metric-button"));
        saveButton.click();

        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("error-message")));
        assertTrue(errorMessage.getText().contains("Invalid value"));
    }
} 