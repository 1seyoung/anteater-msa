package com.anteater.userservice.user.service;


import com.anteater.userservice.dto.LoginRequestDTO;
import com.anteater.userservice.dto.LoginResponseDTO;
import com.anteater.userservice.user.entity.User;

public interface UserService {
    void registerUser(User user);
    LoginResponseDTO login(LoginRequestDTO loginRequest);
    void verifyUser(String token);
    void resetPassword(String email);
    void confirmResetPassword(String token, String newPassword);
    User getUserByUsername(String username);
}