package com.healthylife.infrastructure.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthylife.application.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Around("@annotation(audited)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Audited audited) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        // Capture input parameters
        Map<String, Object> inputParams = new HashMap<>();
        String[] paramNames = signature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                inputParams.put(paramNames[i], objectMapper.valueToTree(args[i]));
            }
        }

        Object result = null;
        Map<String, Object> outputParams = new HashMap<>();
        String entityId = null;

        try {
            // Execute the method
            result = joinPoint.proceed();

            // Capture the result
            if (result != null) {
                outputParams.put("result", objectMapper.valueToTree(result));
                
                // Try to extract entity ID if available
                if (result instanceof Map) {
                    entityId = String.valueOf(((Map<?, ?>) result).get("id"));
                } else {
                    try {
                        entityId = String.valueOf(objectMapper.convertValue(result, Map.class).get("id"));
                    } catch (Exception e) {
                        // Ignore if we can't extract ID
                    }
                }
            }

            // Log successful execution
            auditService.logAction(
                audited.action(),
                audited.entityType(),
                entityId,
                inputParams,
                outputParams,
                String.format("Method %s executed successfully", methodName)
            );

        } catch (Exception e) {
            // Log failed execution
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("error", e.getMessage());
            errorDetails.put("errorType", e.getClass().getSimpleName());

            auditService.logAction(
                audited.action(),
                audited.entityType(),
                entityId,
                inputParams,
                errorDetails,
                String.format("Method %s failed: %s", methodName, e.getMessage())
            );

            throw e;
        }

        return result;
    }
} 