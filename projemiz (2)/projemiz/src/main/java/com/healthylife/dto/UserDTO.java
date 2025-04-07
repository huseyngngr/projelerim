package com.healthylife.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private Integer age;

    @Size(max = 10, message = "Gender must not exceed 10 characters")
    private String gender;

    @DecimalMin(value = "0.0", message = "Weight must be greater than or equal to 0")
    @DecimalMax(value = "500.0", message = "Weight must be less than or equal to 500")
    private Double weight;

    @DecimalMin(value = "0.0", message = "Height must be greater than or equal to 0")
    @DecimalMax(value = "300.0", message = "Height must be less than or equal to 300")
    private Double height;

    private String phoneNumber;

    private LocalDateTime dateOfBirth;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean enabled;
} 