package com.healthylife.infrastructure.monitoring;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MonitoringAspect {

    private final MetricsService metricsService;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object monitorEndpoint(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            
            metricsService.recordRequestDuration(duration);
            metricsService.incrementCustomCounter("app.endpoint.calls",
                    "class", className,
                    "method", methodName);
        }
    }

    @Around("execution(* com.healthylife.interfaces.rest.auth.AuthController.login(..))")
    public Object monitorLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        metricsService.recordLoginAttempt();
        
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            metricsService.recordFailedLogin();
            throw e;
        }
    }
} 