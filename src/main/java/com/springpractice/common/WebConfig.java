package com.springpractice.common;

import com.springpractice.handler.BearerTokenAnnotationResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BearerTokenAnnotationResolver bearerTokenAnnotationResolver;

    public WebConfig(BearerTokenAnnotationResolver bearerTokenAnnotationResolver) {
        this.bearerTokenAnnotationResolver = bearerTokenAnnotationResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(this.bearerTokenAnnotationResolver);
    }
}
