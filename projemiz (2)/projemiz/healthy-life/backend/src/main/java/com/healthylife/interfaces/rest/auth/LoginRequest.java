package com.healthylife.interfaces.rest.auth;

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
public class LoginRequest {
    @NotBlank(message = "Email adresi gereklidir")
    @Email(message = "Geçersiz email formatı")
    private String email;

    @NotBlank(message = "Şifre gereklidir")
    private String password;
} 