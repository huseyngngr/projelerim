package com.healthylife.security;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ApiKeyRotationScheduler {

    private final ApiKeyRepository apiKeyRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Scheduled(cron = "0 0 0 * * *") // Her gün gece yarısı
    @Transactional
    public void rotateApiKeys() {
        // 30 günden eski API key'leri deaktive et
        apiKeyRepository.deactivateOldKeys(LocalDateTime.now().minusDays(30));

        // Aktif kullanıcılar için yeni API key'ler oluştur
        apiKeyRepository.findActiveUsers().forEach(user -> {
            String newApiKey = generateSecureApiKey();
            ApiKey apiKey = new ApiKey();
            apiKey.setUserId(user.getId());
            apiKey.setApiKey(newApiKey);
            apiKey.setCreatedAt(LocalDateTime.now());
            apiKey.setActive(true);
            apiKeyRepository.save(apiKey);
        });
    }

    private String generateSecureApiKey() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

@Entity
@Table(name = "api_keys")
@Data
class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String apiKey;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean active;
} 