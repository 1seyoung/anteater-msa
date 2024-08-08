package com.anteater.userservice.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String profileImage;
    private String bio;
    // 비밀번호는 보안상 제외
}