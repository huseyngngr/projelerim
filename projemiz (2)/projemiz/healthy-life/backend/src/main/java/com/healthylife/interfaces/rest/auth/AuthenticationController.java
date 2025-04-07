package com.healthylife.interfaces.rest.auth;

import com.healthylife.domain.user.User;
import com.healthylife.domain.user.UserRole;
import com.healthylife.infrastructure.exception.ApiError;
import com.healthylife.service.AuthenticationService;
import com.healthylife.interfaces.rest.auth.dto.AuthenticationRequest;
import com.healthylife.interfaces.rest.auth.dto.AuthenticationResponse;
import com.healthylife.interfaces.rest.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Kimlik Doğrulama", description = "Kimlik doğrulama yönetimi API'leri")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Yeni kullanıcı kaydı")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kullanıcı başarıyla kaydedildi"),
        @ApiResponse(responseCode = "400", description = "Geçersiz giriş",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "409", description = "Kullanıcı zaten mevcut",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> register(
            @Parameter(description = "Kullanıcı kayıt bilgileri", required = true)
            @Valid @RequestBody RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole() != null ? request.getRole() : UserRole.USER)
                .build();

        authenticationService.register(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Kullanıcı girişi")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kullanıcı başarıyla giriş yaptı"),
        @ApiResponse(responseCode = "401", description = "Geçersiz kimlik bilgileri",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> login(
            @Parameter(description = "Kullanıcı giriş bilgileri", required = true)
            @Valid @RequestBody LoginRequest request) {
        String token = authenticationService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(
        summary = "Token yenileme",
        description = "Refresh token kullanarak yeni bir access token alır"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Token başarıyla yenilendi",
            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Geçersiz refresh token",
            content = @Content(schema = @Schema(implementation = ApiError.class))
        )
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Parameter(description = "Bearer formatında refresh token")
            @RequestHeader("Authorization") String refreshToken
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
} 