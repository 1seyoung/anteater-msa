package com.anteater.userservice.service;

import com.anteater.userservice.dto.UpdatePasswordDTO;
import com.anteater.userservice.dto.UpdateProfileDTO;
import com.anteater.userservice.entity.User;
import com.anteater.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getProfile(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void updateProfile(String username, UpdateProfileDTO updateProfileDTO) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateProfileDTO.getName() != null) {
            user.setName(updateProfileDTO.getName());
        }
        if (updateProfileDTO.getProfileImage() != null) {
            user.setProfileImage(updateProfileDTO.getProfileImage());
        }
        if (updateProfileDTO.getGreeting() != null) {
            user.setGreeting(updateProfileDTO.getGreeting());
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
}