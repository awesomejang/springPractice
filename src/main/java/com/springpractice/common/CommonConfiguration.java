package com.springpractice.common;

import com.springpractice.filter.ApiTokenFilter;
import com.springpractice.filter.WebLogFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@EnableConfigurationProperties({RedisProperties.class})
public class CommonConfiguration {

    private final RedisProperties props;
    private final RedisConnectionFactory connectionFactory;

    public CommonConfiguration(RedisProperties props, RedisConnectionFactory connectionFactory) {
        this.props = props;
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Add some specific configuration here. Key serializers, etc.
        return template;
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setHostName(props.getHost());
//        config.setPort(props.getPort());
//        config.setPassword("jangredis");
//
//        return new LettuceConnectionFactory(config);
//    }



//    private final JwtTokenProvider jwtTokenProvider;
//
//    public CommonConfiguration(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Bean
//    public FilterRegistrationBean<WebLogFilter> webLogFilterRegistrationBean() {
//        FilterRegistrationBean<WebLogFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new WebLogFilter());
//        registrationBean.addUrlPatterns("/api/*");
//        registrationBean.setName("webLogFilter");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<ApiTokenFilter> ApiTokenFilerRegistrationBean() {
//        FilterRegistrationBean<ApiTokenFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new ApiTokenFilter(jwtTokenProvider));
//        registrationBean.addUrlPatterns("/api/*");
//        registrationBean.setName("apiTokenFilter");
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }

//    @Bean
//    RedisConnectionFactory redisConnectionFactory(RedisProperties props) {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//
//        config.setPassword(props.getPassword());
//
//        return new LettuceConnectionFactory(config);
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }


}

