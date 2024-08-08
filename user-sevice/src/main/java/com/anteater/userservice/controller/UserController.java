package com.anteater.userservice.controller;

import com.anteater.userservice.dto.LoginRequestDTO;
import com.anteater.userservice.dto.LoginResponseDTO;
import com.anteater.userservice.dto.PasswordResetConfirmDTO;
import com.anteater.userservice.dto.PasswordResetDTO;
import com.anteater.userservice.entity.User;
import com.anteater.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * /api/users
 * 회원가입, 로그인 , 로그아웃, 비밀번호 재설정 기능
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // TODO: Implement email verification
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        userService.resetPassword(passwordResetDTO);
        return ResponseEntity.ok("Password reset instructions sent");
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmResetPassword(@RequestBody PasswordResetConfirmDTO confirmDTO) {
        userService.confirmResetPassword(confirmDTO);
        return ResponseEntity.ok("Password reset successful");
    }
}
