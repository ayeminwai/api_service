package com.maybank.apiservice.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseLoggingAspect.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHttpRequestWrapper request = new CustomHttpRequestWrapper(httpRequest);
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String payload = request.getCachedBody();

        logger.info("[REQUEST] Method: " + method + ", URI: " + uri + ", Payload: " + payload);

        filterChain.doFilter(request, response);
    }
}
