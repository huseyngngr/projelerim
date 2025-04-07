package com.healthylife.interfaces.rest.auth;

import com.healthylife.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Ad gereklidir")
    @Size(min = 2, max = 50, message = "Ad 2 ile 50 karakter arasında olmalıdır")
    private String firstName;

    @NotBlank(message = "Soyad gereklidir")
    @Size(min = 2, max = 50, message = "Soyad 2 ile 50 karakter arasında olmalıdır")
    private String lastName;

    @NotBlank(message = "Email adresi gereklidir")
    @Email(message = "Geçersiz email formatı")
    private String email;

    @NotBlank(message = "Şifre gereklidir")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String password;

    private UserRole role;
} 