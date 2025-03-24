package com.healthylife.domain.port;

import com.healthylife.domain.user.User;
import java.util.Optional;
import java.util.UUID;

public interface UserPort {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    void delete(UUID id);
} 