package com.springpractice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springpractice.filter.ApiTokenFilter;
import com.springpractice.filter.WebLogFilter;
import com.springpractice.handler.BearerTokenAnnotationResolver;
import com.springpractice.interceptor.RequestLogMdcInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BearerTokenAnnotationResolver bearerTokenAnnotationResolver;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public WebConfig(BearerTokenAnnotationResolver bearerTokenAnnotationResolver, JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        this.bearerTokenAnnotationResolver = bearerTokenAnnotationResolver;
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(this.bearerTokenAnnotationResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogMdcInterceptor()).excludePathPatterns("/css/**", "/images/**", "/js/**");
    }

    @Bean
    public FilterRegistrationBean<WebLogFilter> webLogFilterRegistrationBean() {
        FilterRegistrationBean<WebLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WebLogFilter());
        registrationBean.addUrlPatterns("/api/v2/*");
        registrationBean.setName("webLogFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ApiTokenFilter> ApiTokenFilerRegistrationBean() {
        FilterRegistrationBean<ApiTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiTokenFilter(jwtTokenProvider, objectMapper));
        registrationBean.addUrlPatterns("/api/v2/*");
        registrationBean.setName("apiTokenFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
