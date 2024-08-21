package com.springpractice.common;

import com.springpractice.common.Properties.ApiJwtProperties;
import com.springpractice.dtos.AuthTokenRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final ApiJwtProperties apiJwtProperties;
    private final SecretKey secretKey;
    private final RedisService redisService;

    public JwtTokenProvider(ApiJwtProperties apiJwtProperties, RedisService redisService) {
        this.apiJwtProperties = apiJwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(apiJwtProperties.getKey().getBytes()); // 암호화 키 바이트 생성 최소, 최대 길이 있다.
        this.redisService = redisService;
    }

    public String generateToken(AuthTokenRequestDto authTokenRequestDto) {
        log.info("Generating token for user: {}", authTokenRequestDto.clientId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientId", authTokenRequestDto.clientId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authTokenRequestDto.clientId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + apiJwtProperties.getExpirationMs()))
                .signWith(secretKey) // 기본은 SHA256, 지정 가능
                .compact();
    }

    public String generateToken(String clientId) {
        log.info("Generating token for user: {}", clientId);
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientId", clientId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + apiJwtProperties.getExpirationMs()))
                .signWith(secretKey) // 기본은 SHA256, 지정 가능
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private String getRefreshTokenFromClientId(String clientId) {
        return redisService.getRefreshToken(clientId);
    }

    public Map<String, String> refreshToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            Claims claims = getClaims(refreshToken);
            if(refreshToken.equals(getRefreshTokenFromClientId(claims.getSubject()))) {
                // token 발급
                String accessToken = generateToken(claims.getSubject());
                String newRefreshToken = generateToken(claims.getSubject());
                redisService.saveRefreshToken(claims.getSubject(), newRefreshToken);
                return Map.of("accessToken", accessToken, "refreshToken", newRefreshToken);
            }
            throw new IllegalStateException("NOT MATCHED REFRESH TOKEN");
        }
        throw new IllegalStateException("Invalid refresh token");
    }
}
