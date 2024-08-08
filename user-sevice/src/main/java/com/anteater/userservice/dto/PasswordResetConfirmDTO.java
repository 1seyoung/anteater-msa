package com.anteater.userservice.dto;

import lombok.Data;

@Data
public class PasswordResetConfirmDTO {
    private String token;
    private String newPassword;
}