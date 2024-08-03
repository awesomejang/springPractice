package com.springpractice.common;

import com.springpractice.filter.WebLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    public FilterRegistrationBean<WebLogFilter> webLogFilterRegistrationBean() {
        FilterRegistrationBean<WebLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WebLogFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setName("webLogFilter");
        return registrationBean;
    }
}

