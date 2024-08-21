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

    private final ApiJwtProperties apiJwtProperties;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final SecretKey secretKey;

    public JwtTokenProvider(ApiJwtProperties apiJwtProperties) {
        this.apiJwtProperties = apiJwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(apiJwtProperties.getKey().getBytes()); // 암호화 키 바이트 생성 최소, 최대 길이 있다.
    }

    public String generateToken(AuthTokenRequestDto authTokenRequestDto) {
        log.info("Generating token for user: {}", authTokenRequestDto.clientId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientId", authTokenRequestDto.clientId());
//        Instant.now().plus(Duration.ofMillis(apiJwtProperties.getExpirationMs()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authTokenRequestDto.clientId())
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

    }

    public Map<String, String> refreshToken(String clientId, String refreshToken) {
        if (validateToken(refreshToken)) {
            Claims claims = getClaims(refreshToken);

            if(claims.getSubject().equalsIgnoreCase()) {

            }
        }
    }
}
