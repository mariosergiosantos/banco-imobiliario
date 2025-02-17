package com.nossogame.bancoimobiliario.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MetricAspect {

    private static final Logger log = LoggerFactory.getLogger(MetricAspect.class);

    @Value("${spring.application.name}")
    private String appName;

    @Around("@annotation()")
    public Object sendMetric(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            log.info("Method {} execution time in {} Millis", joinPoint.getSignature().getName(), duration);
        }

        return result;
    }
}