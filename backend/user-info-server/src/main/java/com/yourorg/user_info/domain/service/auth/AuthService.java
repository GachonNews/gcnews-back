package com.yourorg.user_info.domain.service.auth;

import com.yourorg.user_info.adapter.in.dto.response.LoginResponseDto;
import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.port.in.auth.AuthPort;
import com.yourorg.user_info.port.out.auth.AuthRepositoryPort;
import com.yourorg.user_info.util.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthPort {

    private final AuthRepositoryPort authRepositoryPort; // 인증 관련 Repository Port
    private final JwtProvider jwtProvider;               // JWT 토큰 생성기

    public AuthService(AuthRepositoryPort authRepositoryPort, JwtProvider jwtProvider) {
        this.authRepositoryPort = authRepositoryPort;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User signup(String loginId, String password) {
        // 이미 존재하는 loginId가 있으면 예외 발생
        if (authRepositoryPort.findByLoginId(loginId) != null) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        // 주의: 실제 서비스는 password 암호화 필요!
        User user = new User(null, loginId, password); // 새 User 생성
        return authRepositoryPort.save(user);          // 저장 후 반환
    }

    @Override
    public LoginResponseDto login(String loginId, String password) {
        // loginId로 사용자 조회
        User user = authRepositoryPort.findByLoginId(loginId);
        // 사용자가 없거나 비밀번호가 다르면 예외 발생
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("로그인 실패");
        }
        // JWT 토큰 생성
        String token = jwtProvider.generateToken(user.getUserId().toString());

        LoginResponseDto dto = new LoginResponseDto(token);

        // 로그인 응답 DTO 반환 (토큰 포함)
        return dto;
    }
}
