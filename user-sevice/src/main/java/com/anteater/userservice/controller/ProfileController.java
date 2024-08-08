package com.anteater.userservice.controller;

import com.anteater.userservice.dto.UpdatePasswordDTO;
import com.anteater.userservice.dto.UpdateProfileDTO;
import com.anteater.userservice.entity.User;
import com.anteater.userservice.service.ProfileService;
import com.anteater.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * /api/prifile
 * 프로필 조회, 업데이트, 비밀번호 변경 기능
 */

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User profile = profileService.getProfile(userDetails.getUsername());
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody UpdateProfileDTO updateProfileDTO) {
        try {
            profileService.updateProfile(userDetails.getUsername(), updateProfileDTO);
            return ResponseEntity.ok().body("Profile updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        try {
            profileService.updatePassword(userDetails.getUsername(), updatePasswordDTO);
            return ResponseEntity.ok().body("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}