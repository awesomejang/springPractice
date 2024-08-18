package com.springpractice.common.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@Component
@ConfigurationProperties(prefix = "authentication-jwt")
public class ApiJwtProperties {
    private final String key;
    private final long expirationMs;
    private final long refreshExpirationDay;

    public ApiJwtProperties(String key, long expirationMs, long refreshExpirationDay) {
        this.key = key;
        this.expirationMs = expirationMs;
        this.refreshExpirationDay = refreshExpirationDay;
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
}
