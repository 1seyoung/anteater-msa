package com.anteater.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;


    //이메일 인증 링크를 보내는 메소드
    public void sendVerificationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your email");
        message.setText("Please click on the link to verify your email: "
                + "http://yourdomain.com/verify-email?token=" + token);
        emailSender.send(message);
    }

    //비밀번호 재설정 이메일을 보내는 메소드
    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset your password");
        message.setText("Please click on the link to reset your password: "
                + "http://yourdomain.com/reset-password?token=" + token);
        emailSender.send(message);
    }
}