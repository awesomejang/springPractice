package com.springpractice.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@EnableConfigurationProperties({RedisProperties.class})
public class CommonConfiguration {

    private final RedisProperties props;
    private final RedisConnectionFactory connectionFactory;
    private final EntityManager em;

    public CommonConfiguration(RedisProperties props, RedisConnectionFactory connectionFactory, EntityManager em) {
        this.props = props;
        this.connectionFactory = connectionFactory;
        this.em = em;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }

    @Primary
    @Bean(name = "mainJpaQueryFactory")
    public JPAQueryFactory mainJpaQueryFactory(@Qualifier("mainEntityManagerFactory")EntityManagerFactory mainEntityManagerFactory) {
        return new JPAQueryFactory(mainEntityManagerFactory.createEntityManager());
    }

    @Bean(name = "subJpaQueryFactory")
    public JPAQueryFactory subJpaQueryFactory(@Qualifier("subEntityManagerFactory")EntityManagerFactory subEntityManagerFactory) {
        return new JPAQueryFactory(subEntityManagerFactory.createEntityManager());
    }
}

