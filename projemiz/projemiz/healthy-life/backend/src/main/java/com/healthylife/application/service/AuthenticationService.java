package com.healthylife.application.service;

import com.healthylife.domain.port.UserPort;
import com.healthylife.domain.user.User;
import com.healthylife.domain.user.UserRole;
import com.healthylife.infrastructure.exception.AuthenticationException;
import com.healthylife.infrastructure.exception.BusinessException;
import com.healthylife.infrastructure.security.JwtService;
import com.healthylife.interfaces.rest.auth.dto.AuthenticationRequest;
import com.healthylife.interfaces.rest.auth.dto.AuthenticationResponse;
import com.healthylife.interfaces.rest.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserPort userPort;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userPort.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists", "EMAIL_EXISTS");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .enabled(true)
                .build();

        var savedUser = userPort.save(user);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return buildAuthResponse(savedUser, accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            var user = (User) authentication.getPrincipal();
            var accessToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            return buildAuthResponse(user, accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid email or password", "INVALID_CREDENTIALS");
        }
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new AuthenticationException("Invalid refresh token", "INVALID_REFRESH_TOKEN");
        }

        refreshToken = refreshToken.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);
        
        if (userEmail == null) {
            throw new AuthenticationException("Invalid refresh token", "INVALID_REFRESH_TOKEN");
        }

        var user = userPort.findByEmail(userEmail)
                .orElseThrow(() -> new AuthenticationException("User not found", "USER_NOT_FOUND"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new AuthenticationException("Invalid refresh token", "INVALID_REFRESH_TOKEN");
        }

        var accessToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefreshToken(user);

        return buildAuthResponse(user, accessToken, newRefreshToken);
    }

    private AuthenticationResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600) // 1 hour
                .userId(user.getId().toString())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
} 