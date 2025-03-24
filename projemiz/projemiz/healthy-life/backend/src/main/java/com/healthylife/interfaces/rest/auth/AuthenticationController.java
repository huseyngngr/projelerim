package com.healthylife.interfaces.rest.auth;

import com.healthylife.application.service.AuthenticationService;
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
@Tag(name = "Authentication", description = "Kimlik doğrulama ve kullanıcı yönetimi API'leri")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
        summary = "Yeni kullanıcı kaydı",
        description = "Sisteme yeni bir kullanıcı kaydeder ve JWT token döner"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Başarılı kayıt",
            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Geçersiz istek (örn: email zaten kayıtlı)",
            content = @Content(schema = @Schema(implementation = ApiError.class))
        )
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
        summary = "Kullanıcı girişi",
        description = "Email ve şifre ile giriş yaparak JWT token alır"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Başarılı giriş",
            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Geçersiz kimlik bilgileri",
            content = @Content(schema = @Schema(implementation = ApiError.class))
        )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
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