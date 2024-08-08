package com.anteater.userservice.profile.service;

import com.anteater.userservice.dto.UpdatePasswordDTO;
import com.anteater.userservice.dto.UpdateProfileDTO;
import com.anteater.userservice.user.entity.User;

public interface ProfileService {
    User getProfile(String username);
    void updateProfile(String username, UpdateProfileDTO updateProfileDTO);
    void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO);
}
/*
public 으로 쓰냐 안쓰냐?
public 으로 쓰지 않으면 같은 패키지 내에서만 접근
서비스 계층을 외부로 부터 숨시고, 컨트롤러를 통해서만 접근하도록 강제 가능

ProfileService | ProfileServiceImpl 를 구분한 이유?
--------------------------------------------------
- Impl은 구현체라는 의미로 붙이는 것

- 인터페이스(ProfileService) 는 무엇을 할 수 있는가 정의
- 구현체(ProfileServiceImpl) 는 어떻게 할 것인가 정의

유연성과 확장성 제공

인터페이스를 사용하면 테스트 시 실제 구현체 대신 모의 객체(Mock Object)를 사용할 수 있음 -> 테스트 용이


예를 들어, 나중에 프로필 서비스의 다른 구현이 필요해진다면 (예: 캐시를 사용하는 버전), 새로운 구현체를 만들고 주입만 바꾸기
 */
