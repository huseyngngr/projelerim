package com.healthylife.interfaces.rest.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Kullanıcı girişi için gerekli bilgiler")
public class AuthenticationRequest {
    @Schema(description = "Kullanıcının email adresi", example = "user@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Kullanıcının şifresi", example = "password123")
    @NotBlank(message = "Password is required")
    private String password;
} 