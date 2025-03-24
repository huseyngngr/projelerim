package com.healthylife.infrastructure.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthylife.application.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditAspectTest {

    @Mock
    private AuditService auditService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private AuditAspect auditAspect;

    private Audited audited;
    private Object[] args;
    private String[] paramNames;

    @BeforeEach
    void setUp() {
        audited = new Audited() {
            @Override
            public String action() {
                return "TEST_ACTION";
            }

            @Override
            public String entityType() {
                return "TEST_ENTITY";
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Audited.class;
            }
        };

        args = new Object[]{"testArg"};
        paramNames = new String[]{"param1"};

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(this.getClass().getMethods()[0]);
        when(methodSignature.getParameterNames()).thenReturn(paramNames);
        when(joinPoint.getArgs()).thenReturn(args);
    }

    @Test
    void auditMethod_SuccessfulExecution_ShouldLogAction() throws Throwable {
        // Arrange
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", "123");
        when(joinPoint.proceed()).thenReturn(resultMap);
        when(objectMapper.valueToTree(any())).thenReturn(null);

        // Act
        Object result = auditAspect.auditMethod(joinPoint, audited);

        // Assert
        assertThat(result).isEqualTo(resultMap);
        verify(auditService).logAction(
            eq(audited.action()),
            eq(audited.entityType()),
            eq("123"),
            any(),
            any(),
            anyString()
        );
    }

    @Test
    void auditMethod_FailedExecution_ShouldLogErrorAndRethrow() throws Throwable {
        // Arrange
        RuntimeException exception = new RuntimeException("Test error");
        when(joinPoint.proceed()).thenThrow(exception);
        when(objectMapper.valueToTree(any())).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> auditAspect.auditMethod(joinPoint, audited))
                .isEqualTo(exception);

        verify(auditService).logAction(
            eq(audited.action()),
            eq(audited.entityType()),
            isNull(),
            any(),
            argThat(map -> map.containsKey("error") && map.containsKey("errorType")),
            anyString()
        );
    }

    @Test
    void auditMethod_WithNullResult_ShouldLogActionWithoutEntityId() throws Throwable {
        // Arrange
        when(joinPoint.proceed()).thenReturn(null);
        when(objectMapper.valueToTree(any())).thenReturn(null);

        // Act
        Object result = auditAspect.auditMethod(joinPoint, audited);

        // Assert
        assertThat(result).isNull();
        verify(auditService).logAction(
            eq(audited.action()),
            eq(audited.entityType()),
            isNull(),
            any(),
            any(),
            anyString()
        );
    }
} 