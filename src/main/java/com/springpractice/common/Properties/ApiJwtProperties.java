package com.springpractice.common.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "authentication-jwt")
public class ApiJwtProperties {
    private String key;
    private long expirationMs;

    public String getKey() {
        return key;
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}
