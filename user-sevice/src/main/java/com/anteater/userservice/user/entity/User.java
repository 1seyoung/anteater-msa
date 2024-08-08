package com.anteater.userservice.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //id를 long, int, string 중 어떤 것으로 할지?
    //id를 long으로 하는 이유는? --> 더 큰 범위의 값을 저장할 수 있습니다 (약 -9백경 ~ +9백경). int는 -21억 ~ +21억 // 미래의 확장성을 고려했을 때 더 안전 //대규모 데이터를 다루는 시스템에 적합
    //id를 integer 로 하는 이유는? --> 더 작은 범위의 값을 저장할 수 있습니다 (약 -21억 ~ +21억). long은 -9백경 ~ +9백경 // 더 적은 메모리를 사용하므로 더 빠르게 처리 가능 //데이터가 많지 않은 경우에 적합
    //극도로 높은 성능이 요구되는 경우, Integer가 약간의 이점을 제공할

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column
    private boolean verified;

    @Column
    private String verificationToken;

// 비밀번호 재설정 관련 필드 (향후 구현 예정)
// @Column
// private String resetPasswordToken;

}