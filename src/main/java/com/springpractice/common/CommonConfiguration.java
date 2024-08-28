package com.springpractice.common;

import com.springpractice.filter.ApiTokenFilter;
import com.springpractice.filter.WebLogFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    public CommonConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public FilterRegistrationBean<WebLogFilter> webLogFilterRegistrationBean() {
        FilterRegistrationBean<WebLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WebLogFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setName("webLogFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ApiTokenFilter> ApiTokenFilerRegistrationBean() {
        FilterRegistrationBean<ApiTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiTokenFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setName("apiTokenFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }

}

