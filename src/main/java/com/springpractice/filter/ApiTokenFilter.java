package com.springpractice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springpractice.common.JwtTokenProvider;
import com.springpractice.common.RedisService;
import com.springpractice.dtos.CommonResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public ApiTokenFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                writeJsonToResponse(response, HttpStatus.UNAUTHORIZED, CommonResponseDto.fail("Invalid token").toJson());
                response.getWriter().flush();
                return;
            }
        } else {
            writeJsonToResponse(response, HttpStatus.UNAUTHORIZED, CommonResponseDto.fail("Authorization header is required").toJson());
            response.getWriter().flush();
        }
        filterChain.doFilter(request, response);
    }


    private void writeJsonToResponse(HttpServletResponse response, HttpStatus httpStatus, String message) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(httpStatus.value());
        response.getWriter().write(message);
    }
}
