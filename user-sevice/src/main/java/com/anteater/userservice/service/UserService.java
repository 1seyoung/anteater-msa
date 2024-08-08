package com.anteater.userservice.service;


import com.anteater.userservice.dto.LoginRequestDTO;
import com.anteater.userservice.dto.LoginResponseDTO;
import com.anteater.userservice.dto.PasswordResetConfirmDTO;
import com.anteater.userservice.dto.PasswordResetDTO;
import com.anteater.userservice.entity.User;
import com.anteater.userservice.repository.UserRepository;
import com.anteater.userservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UserService {
    void registerUser(User user);
    LoginResponseDTO login(LoginRequestDTO loginRequest);
    void verifyUser(String token);
    void resetPassword(String email);
    void confirmResetPassword(String token, String newPassword);
    User getUserByUsername(String username);
}