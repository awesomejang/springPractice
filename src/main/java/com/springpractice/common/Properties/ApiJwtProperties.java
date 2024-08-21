package com.springpractice.common.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@Component
@ConfigurationProperties(prefix = "authentication-jwt")
public class ApiJwtProperties {
    private final String key;
    private final long expirationMs;
    private final long refreshExpirationDay;
    private final String keyPattern;

    public ApiJwtProperties(String key, long expirationMs, long refreshExpirationDay, String keyPattern) {
        this.key = key;
        this.expirationMs = expirationMs;
        this.refreshExpirationDay = refreshExpirationDay;
        this.keyPattern = keyPattern;
    }

    public String getKey() {
        return key;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public long getRefreshExpirationDay() {
        return refreshExpirationDay;
    }

    public String getKeyPattern() {
        return keyPattern;
    }
}
