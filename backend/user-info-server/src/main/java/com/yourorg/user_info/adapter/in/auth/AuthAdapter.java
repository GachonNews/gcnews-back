package com.yourorg.user_info.adapter.in.auth;

import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.port.in.auth.AuthPort;
import com.yourorg.user_info.adapter.in.dto.request.LoginRequestDto;
import com.yourorg.user_info.adapter.in.dto.request.SignupRequestDto;
import com.yourorg.user_info.adapter.in.dto.response.LoginResponseDto;
import com.yourorg.user_info.adapter.in.dto.response.OurApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
public class AuthAdapter {

    private final AuthPort authPort;

    @Operation(summary = "회원가입")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "회원가입 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "회원가입 성공 예시",
                    value = """
                    {
                      "status": "success",
                      "data": {
                        "userId": 101,
                        "loginId": "arin123",
                        "name": "아린",
                      },
                      "message": null
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "중복 아이디(이미 존재)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "중복 아이디 실패 예시",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "이미 등록된 아이디입니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping("/signup")
    public ResponseEntity<OurApiResponse<User>> signup(@RequestBody SignupRequestDto req) {
        User user = authPort.signup(req.getLoginId(), req.getPassword());
        if (user == null) {
            // 이미 존재 등 예시, 실무에선 서비스에서 결과 분리 추천
            return ResponseEntity.status(409)
                .body(new OurApiResponse<>("fail", null, "이미 등록된 아이디입니다."));
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", user, null)
        );
    }

    @Operation(summary = "로그인")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "로그인 성공 예시",
                    value = """
                    {
                      "status": "success",
                      "data": {
                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
                        "userId": 101,
                      },
                      "message": null
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "로그인 실패(아이디/비밀번호 불일치)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "로그인 실패 예시",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping("/login")
    public ResponseEntity<OurApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto req) {
        LoginResponseDto userResponse = authPort.login(req.getLoginId(), req.getPassword());
        if (userResponse == null) {
            return ResponseEntity.status(401)
                .body(new OurApiResponse<>("fail", null, "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."));
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", userResponse, null)
        );
    }
}
