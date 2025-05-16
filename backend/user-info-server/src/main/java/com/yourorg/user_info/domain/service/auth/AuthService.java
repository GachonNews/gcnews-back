package com.yourorg.user_info.domain.service.auth;

import com.yourorg.user_info.adapter.in.dto.LoginResponsedto;
import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.port.in.auth.AuthPort;
import com.yourorg.user_info.port.out.auth.AuthRepositoryPort;
import com.yourorg.user_info.util.JwtProvider;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements AuthPort {

    private final AuthRepositoryPort authRepositoryPort;
    private final JwtProvider jwtProvider;

    public AuthService(AuthRepositoryPort authRepositoryPort, JwtProvider jwtProvider) {
        this.authRepositoryPort = authRepositoryPort;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User signup(String loginId, String password) {
        if (authRepositoryPort.findByLoginId(loginId) != null) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        // 실제로는 password 암호화 필요!
        User user = new User(null, loginId, password);
        return authRepositoryPort.save(user);
    }

    @Override
    public LoginResponsedto login(String loginId, String password) {
        User user = authRepositoryPort.findByLoginId(loginId);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("로그인 실패");
        }
        String token = jwtProvider.generateToken(user.getUserId().toString());

        LoginResponsedto dto = new LoginResponsedto(user.getUserId(), token);

        // JWT 토큰 발급
        return dto;
    }
}
