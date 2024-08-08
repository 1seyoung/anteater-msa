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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // UserRepository를 주입

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder를 주입

    @Autowired
    private AuthenticationManager authenticationManager; //인증 관리자 주입

    @Autowired
    private JwtTokenProvider jwtTokenProvider; //JWT 토큰 생성기 주입

    @Autowired
    private EmailService emailService; //이메일 서비스 주입


    //사용자 등록 메서드
    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 인코딩
        user.setVerificationToken(UUID.randomUUID().toString()); // 인증 토큰 생성
        userRepository.save(user);

        // TODO: Send verification email
        emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
    }

    //로그인 메서드
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 설정
        String jwt = jwtTokenProvider.generateToken(authentication); // JWT 토큰 생성

        return new LoginResponseDTO(jwt); //로그인 응답 반환
    }

    // 로그아웃 메서드 (현재 구현되지 않음)
    public void logout(String token) {
        // TODO: Implement logout logic (e.g., add token to blacklist)
    }

    // 비밀번호 재설정 요청 메서드
    public void resetPassword(PasswordResetDTO passwordResetDTO) {
        User user = userRepository.findByEmail(passwordResetDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resetToken = UUID.randomUUID().toString(); // 재설정 토큰 생성
        user.setResetPasswordToken(resetToken);
        userRepository.save(user);

        // // 비밀번호 재설정 이메일 전송
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    // 비밀번호 재설정 확인 메서드
    public void confirmResetPassword(PasswordResetConfirmDTO confirmDTO) {
        User user = userRepository.findByResetPasswordToken(confirmDTO.getResetToken())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        user.setPassword(passwordEncoder.encode(confirmDTO.getNewPassword()));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    // 이메일 인증 확인 메서드
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }
}