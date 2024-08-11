package com.springpractice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import java.io.IOException;

@Slf4j
public class WebLogFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

        // body 로깅
        logger.info(DEFAULT_BEFORE_MESSAGE_PREFIX + new String(cachedBodyHttpServletRequest.getInputStream().readAllBytes()) + DEFAULT_BEFORE_MESSAGE_SUFFIX);

        filterChain.doFilter(cachedBodyHttpServletRequest, response);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        log.info("Request: {}", message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        log.info("Response: {}", message);
    }
}
