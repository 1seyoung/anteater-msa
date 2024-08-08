package com.anteater.userservice.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendVerificationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("이메일 인증");
        message.setText("인증을 완료하려면 다음 링크를 클릭하세요: "
                + "http://localhost:8080/api/users/verify?token=" + token);
        emailSender.send(message);
    }
}