package com.springpractice.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
public class WebLogFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        log.info("Request: {}", message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        log.info("Response: {}", message);
    }
}
