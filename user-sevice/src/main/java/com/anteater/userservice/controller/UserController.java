package com.anteater.userservice.controller;

import com.anteater.userservice.dto.*;
import com.anteater.userservice.entity.User;
import com.anteater.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * /api/users
 * 회원가입, 로그인 , 로그아웃, 비밀번호 재설정 기능
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            userService.registerUser(registrationDTO);
            return ResponseEntity.ok("User registered successfully. Please check your email for verification.");
        } catch (UserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = userService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
            userService.logout(token);
            return ResponseEntity.ok("Logged out successfully");
        } catch (UserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        try {
            userService.resetPassword(passwordResetDTO.getEmail());
            return ResponseEntity.ok("Password reset instructions sent to your email");
        } catch (UserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody PasswordResetConfirmDTO confirmDTO) {
        try {
            userService.confirmResetPassword(confirmDTO.getToken(), confirmDTO.getNewPassword());
            return ResponseEntity.ok("Password reset successful");
        } catch (UserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        try {
            userService.verifyUser(token);
            return ResponseEntity.ok("Email verification completed successfully");
        } catch (UserServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}