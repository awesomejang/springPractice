package com.springpractice.common;

import com.springpractice.common.Properties.ApiJwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

    public JwtTokenProvider(ApiJwtProperties apiJwtProperties) {
        this.apiJwtProperties = apiJwtProperties;
    }

    public String generateToken(String username) {
        log.info("Generating token for user: {}", username);
        Map<String, Object> claims = new HashMap<>();
        SecretKey secretKey = Keys.hmacShaKeyFor(apiJwtProperties.getKey().getBytes());
//        Instant.now().plus(Duration.ofMillis(apiJwtProperties.getExpirationMs()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + apiJwtProperties.getExpirationMs()))
                .signWith(secretKey)
                .compact();
    }
}
