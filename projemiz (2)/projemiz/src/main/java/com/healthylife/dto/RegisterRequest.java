package com.healthylife.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDateTime dateOfBirth;
    private String gender;
} 