package com.springpractice.common;

import com.springpractice.common.Properties.ApiJwtProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ApiJwtProperties apiJwtProperties;

    public RedisService(RedisTemplate<String, String> redisTemplate, ApiJwtProperties apiJwtProperties) {
        this.redisTemplate = redisTemplate;
        this.apiJwtProperties = apiJwtProperties;
    }

    public void saveRefreshToken(String clientId, String refreshToken) {
        redisTemplate.opsForValue().set(clientId, refreshToken, apiJwtProperties.getRefreshExpirationDay(), TimeUnit.DAYS);
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }
}
