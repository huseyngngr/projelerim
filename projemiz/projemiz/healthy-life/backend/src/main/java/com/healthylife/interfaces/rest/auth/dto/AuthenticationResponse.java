package com.healthylife.interfaces.rest.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Kimlik doğrulama yanıtı")
public class AuthenticationResponse {
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    @JsonProperty("access_token")
    private String accessToken;
    
    @Schema(description = "JWT refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @Schema(description = "Token tipi", example = "Bearer")
    @JsonProperty("token_type")
    private String tokenType = "Bearer";
    
    @Schema(description = "Token geçerlilik süresi (saniye)", example = "3600")
    @JsonProperty("expires_in")
    private long expiresIn;
    
    @Schema(description = "Kullanıcı ID'si", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("user_id")
    private String userId;
    
    @Schema(description = "Kullanıcı email adresi", example = "john.doe@example.com")
    @JsonProperty("email")
    private String email;
    
    @Schema(description = "Kullanıcının adı", example = "John")
    @JsonProperty("first_name")
    private String firstName;
    
    @Schema(description = "Kullanıcının soyadı", example = "Doe")
    @JsonProperty("last_name")
    private String lastName;
    
    @Schema(description = "Kullanıcı rolü", example = "USER")
    @JsonProperty("role")
    private String role;
} 