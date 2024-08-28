package com.springpractice.filter;

import com.springpractice.common.JwtTokenProvider;
import com.springpractice.common.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public ApiTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is required");
        }
        filterChain.doFilter(request, response);
    }
}
