package com.yourorg.user_info.adapter.in.auth;

import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.port.in.auth.AuthPort;
import com.yourorg.user_info.adapter.in.dto.LoginRequestdto;
import com.yourorg.user_info.adapter.in.dto.SignupRequestdto;
import com.yourorg.user_info.adapter.in.dto.LoginResponsedto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
public class AuthAdapter {

    private final AuthPort authPort;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequestdto req) {
        return ResponseEntity.ok(authPort.signup(req.getLoginId(), req.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponsedto> login(@RequestBody LoginRequestdto req) {
        LoginResponsedto userResponse = authPort.login(req.getLoginId(), req.getPassword());
        return ResponseEntity.ok(userResponse);
    }
}
