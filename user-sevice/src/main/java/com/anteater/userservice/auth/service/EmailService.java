package com.anteater.userservice.auth.service;

public interface EmailService {
    void sendVerificationEmail(String to, String token);
}