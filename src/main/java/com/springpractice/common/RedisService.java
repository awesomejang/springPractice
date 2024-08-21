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

    // TODO: refresh token 저장전에 패턴 만들어서 저장
    // redis에서 key로 검색할때 key:key 이런 패턴으로 저장하고 검색할때 key:* 이런식으로 검색하게끔 지금 생각은 user:clientId 이런식으로 해야할듯
    public void saveRefreshToken(String clientId, String refreshToken) {
        redisTemplate.opsForValue().set(String.format(apiJwtProperties.getKeyPattern(), clientId), refreshToken, apiJwtProperties.getRefreshExpirationDay(), TimeUnit.DAYS);
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }
}
