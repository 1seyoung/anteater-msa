package com.anteater.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration, @EnableWebSecurity 는 이 클래스가 spring security 설정 클래스임을 의미함
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //bean : 스프링이 관리하는 객체
    //bean 을 생성하는 의미 : spring container 에서 해당 객체를 관리하겠다는 의미
    //bean을 왜 사용하나? 재사용성(여러곳에서 관리), 의존성 관리(객체간 관계), 일관성(애플리케이션 전체에서 동일한 설정의 객체 사용)



    //PasswordEncoder 를 빈으로 만드는 이유 : 비밀 번호 암화화 방식의 일관성, 애플리케이션의 여러 부분에서 사용, 나중에 암호화 방식을 수정해야하면  여기만 수정
    //제어의 역전(IoC) : 개발자가 직접 객체를 생성하지 않고 스프링이 대신 객체를 생성하고 관리
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //스프링 시큐리티에서 제공하는 클래스, 비밀번호 암호화
        //BCrypt 알고리즘 : 비밀번호 해싱을 위해 설계된 암호화 함수
        //같은 비밀번호라도 매번 다른 해시를 생성하여 보안성 높임
        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        //String rawPassword = "myPassword123";
        //String encodedPassword = encoder.encode(rawPassword);
    }


    //SecurityFilterChain : HTTP 요청에 대한 접근 권한을 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests //요청에 대한 접근 권한 설정
                        .requestMatchers("/api/public/**").permitAll() // /api/public/** 경로는 모든 사용자에게 허용
                        .anyRequest().authenticated() //나머지 요청은 인증된 사용자만 허용
                )
                .csrf(csrf -> csrf.disable());
                //csrf : Cross Site Request Forgery,
                // 웹사이트 취약점 공격의 하나로, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)를 특정 웹사이트에 요청하게 하는 공격
                //CSRF 보호를 비활성화하는 것은 보안상 위험할 수 있으므로, API 서버 등 특별한 경우가 아니라면 활성화된 상태로 두는 것이 좋음



        return http.build();
    }
}