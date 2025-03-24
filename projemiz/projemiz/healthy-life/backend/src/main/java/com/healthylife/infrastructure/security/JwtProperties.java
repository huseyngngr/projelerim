package com.healthylife.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private String secretKey;
    private long expiration;
    private RefreshToken refreshToken;

    @Getter
    @Setter
    public static class RefreshToken {
        private long expiration;
    }
} 