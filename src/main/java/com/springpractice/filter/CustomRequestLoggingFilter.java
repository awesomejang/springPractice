package com.springpractice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Http Request Body에 접근하면 정책에 따라 body 내용이 사라지기 때문에 wrapping 작업이 필요
public class CustomRequestLoggingFilter extends HttpServletRequestWrapper {

    private final byte[] requestBody;

    public CustomRequestLoggingFilter(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = request.getInputStream().readAllBytes();
    }

    public ServletInputStream getInputStream() {
        return new CustomRequestLoggingFilter()
    }
}
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//
//
//    }
//}
