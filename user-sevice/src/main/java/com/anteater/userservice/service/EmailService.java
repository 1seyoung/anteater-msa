package com.anteater.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface EmailService {
    void sendVerificationEmail(String to, String token);
}