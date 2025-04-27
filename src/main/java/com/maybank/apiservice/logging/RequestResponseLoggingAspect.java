package com.maybank.apiservice.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestResponseLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);
    private final ObjectMapper objectMapper;

    @Around("execution(* com.maybank.apiservice.controller.*.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {

        // Proceed with the controller method
        Object response = joinPoint.proceed();

        // Log the response details in JSON format
        String responseJson = (response instanceof String) ? response.toString() : objectMapper.writeValueAsString(response);

        // Log the response details
        logger.info("[RESPONSE] Status: 200, Response: {}", responseJson);

        return response;
    }
}
