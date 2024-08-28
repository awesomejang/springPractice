package com.springpractice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@Slf4j
public class WebLogFilter extends AbstractRequestLoggingFilter {

    private static final Logger logger = LoggerFactory.getLogger(WebLogFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    // 필터 체인에 요청을 전달하기 전에, 요청의 본문을 읽거나 로깅하는 등의 작업을 수행
    // 필터 체인이 실행될 때 가장 먼저 호출되는 메서드로, 요청이 들어오면 필터에서 가장 먼저 실행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(response);
//        cachedBodyHttpServletResponse.setHeader("header-name", "header-value");
        filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

        // body 로깅
        byte[] cachedContent = cachedBodyHttpServletResponse.getCachedContent();
        logger.info("========================================");
        logger.info("REQUEST METHOD: [{}]", request.getMethod());
        logger.info("REQUEST URL: {}", request.getRequestURL());

        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            String requestBody = new String(cachedBodyHttpServletRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            logger.info("REQUEST BODY: {}", objectMapper.writeValueAsString(objectMapper.readValue(requestBody, Object.class)));
        }
//        logger.info("RESPONSE BODY: {}", new String(cachedBodyHttpServletResponse.getCachedContent(), StandardCharsets.UTF_8));
        logger.info("========================================");

        response.getOutputStream().write(cachedContent);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }
}
