package com.anteater.userservice.profile.controller;

import com.anteater.userservice.dto.UpdatePasswordDTO;
import com.anteater.userservice.dto.UpdateProfileDTO;
import com.anteater.userservice.user.entity.User;
import com.anteater.userservice.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * /api/prifile
 * 프로필 조회, 업데이트, 비밀번호 변경 기능
 */

//@RestController : 모든 메서드의 반환값을 HTTP 응답 본문으로 직접 전송 (View를 거치지 않음).
@RestController //REST API 처리한다고 spring에 알려줌
@RequestMapping("/api/profile") //모든 요청이 "/api/profile" 로 시작 (기본 경로 지정)
public class ProfileController { //public class -> 외부 접근 가능, 스프링이 접근할 수 있어야 함 -> public

    // profileservice 를 사용하기 위한 의존성 주입
    //@Autowired -> 의존성 주입 역할
    private final ProfileService profileService;
    //private final -> profileService 를 변경 불가능(immutable)하게 만듬
    //thread safe 보장하고 의도치 않은 수정 방지 가능

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //프로필 조회 메서드
    //HTTP GET 요청을 매핑, 경로를 지정 x -> 클래스의 기본 경로인 ("/api/profile")
    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        //ResponseEntity<?> : 다양한 타입의 응답 반환 가능, ? -> 와일드카드 타입 , 어떤 타입이든 반환할 수 있음을 의미함
        //@AuthenticationPrincipal : Spring Security에서 현재 인증된 사용자의 정보를 주입
        //UserDetals : Spring Security에서 제공하는 인터페이스, 사용자의 핵심 정보 나타냄(인증된 사용자의 정보를 표준화된 방식으로 제공)
        //@AuthenticationPrincipal 어노테이션과 함께 사용되면, Spring Security가 자동으로 현재 인증된 사용자의 UserDetails 객체를 주입
        //작동 방식 1. 사용자 로그인 2. spring security 인증 처리 3. 인증 정보가 security context 에 저장 4. 컨트롤러 메서드가 호출될 때, spring이 SecurityContext에서 사죵자 정보를 가져와 UserDetail 객체로 주입
        try {
            User profile = profileService.getProfile(userDetails.getUsername());
            return ResponseEntity.ok(profile); //HTTP 200 OK 상태와 함께 응답을 반환
        } catch (RuntimeException e) {
            //RuntimeException -> 사용자를 찾을 수 없는 경우, 프로필 업데이트 중 문제가 발생한 경우, 비밀번호 변경 시 현재 비밀 번호가 일치하지 않는 경우?
            //여기서 문제 발생하면 다양하게 예외처리를 시도?
            return ResponseEntity.badRequest().body(e.getMessage()); //HTTP 400 Bad Request 상태와 함께 오류 메시지를 반환
        }
    }

    //프로필 업데이트 메서드
    //HTTP PUT 요청 매핑 (PUT은 리소스 업데이트에 사용)
    @PutMapping
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails, //1
                                           @RequestBody UpdateProfileDTO updateProfileDTO) { //2

        //1 현재 로그인한 사용자의 정보를 Spring Security에서 자동으로 가져와 메서드 파라미터로 주입해주는 기능
        //  컨트롤러에서 별도의 인증 로직 없이 바로 로그인된 사용자의 정보에 접근

        //2 HTTP 요청 본문을 Java 객체(여기서는 UpdateProfileDTO)로 변환
        // HTTP 요청 본문 -> 클라이언트가 서버로 보내는 데이터 / post,put/ ex Json data
        // DTO(Data Transfer Object) : 데이터 전송하기 위한 객체
        // @RequestBody 가 변환 담당 -> spring이 내부적으로 Jsackson 라이브러리를 사용하여 JSON을 Java 객체로 변환
        // Json의 키 이름과 DTO 클래스의 필드 이름 일치해야함
        //클라이언트에서 보낸 데이터를 쉽게 Java에서 사용할 수 있는 객체로 받을 수 있음
        // **클라이언트가 JSON으로 데이터를 보내면, Spring이 자동으로 그 데이터를 UpdateProfileDTO 객체로 변환하여 메서드에 전달
        //                  -> 개발자가 직접 JSON 파싱을 처리할 필요 없이 바로 Java 객체처럼 데이터 다룸

        try {
            profileService.updateProfile(userDetails.getUsername(), updateProfileDTO);
            return ResponseEntity.ok().body("Profile updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //비밀번호 변경 메소드
    // "/api/profile/password" 경로로 오는 PUT 요청을 처리
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