package com.yourorg.user_info.port.out.auth;
import com.yourorg.user_info.domain.entity.User;

public interface AuthRepositoryPort {
    User save(User user);                 // 유저 정보 저장
    User findByLoginId(String loginId);   // 로그인-ID로 유저 정보 찾기
}