package com.yourorg.user_info.port.in.auth;

import com.yourorg.user_info.adapter.in.dto.response.LoginResponseDto;
import com.yourorg.user_info.domain.entity.User;

public interface AuthPort {
    User signup(String loginId, String password, String name, String email); //  회원가입
    LoginResponseDto login(String loginId, String password); // 로그인
}
