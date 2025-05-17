package com.yourorg.user_info.adapter.in.web;

import com.yourorg.user_info.adapter.in.dto.response.UserResponseDto;
import com.yourorg.user_info.adapter.in.dto.response.OurApiResponse;
import com.yourorg.user_info.port.in.web.UserRequestPort;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/user-info/{user_id}/profile")
@RequiredArgsConstructor
public class UserRequestAdapter {
    private final UserRequestPort service;

    @Operation(summary = "유저 프로필 조회")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "유저 정보 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "프로필 조회 성공",
                    value = """
                    {
                        "status": "success",
                        "data": {
                            "name": "testuser",
                            "gender": null,
                            "age": 0,
                            "joinAt": null,
                            "img": null
                        },
                        "message": null
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "해당 유저 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "유저 없음",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "해당 유저를 찾을 수 없습니다."
                    }
                    """
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<OurApiResponse<UserResponseDto>> getUser(@PathVariable("user_id") Long userId) {
        UserResponseDto userDto = service.getUser(userId);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new OurApiResponse<>("fail", null, "해당 유저를 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", userDto, null)
        );
    }

    @Operation(summary = "유저 프로필 등록/수정")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", description = "등록/수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "등록/수정 성공",
                    value = """
                    {
                        "status": "success",
                        "data": {
                            "name": "아린",
                            "gender": "여",
                            "age": 24,
                            "joinAt": "2025-01-01",
                            "img": "img_url"
                        },
                        "message": null
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "저장 실패",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "저장 실패",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "유저 정보를 저장할 수 없습니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<OurApiResponse<UserResponseDto>> upsertUser(
            @PathVariable("user_id") Long userId,
            @RequestBody UserResponseDto userDto) {
        UserResponseDto dto = service.upsertUser(userId, userDto);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new OurApiResponse<>("fail", null, "유저 정보를 저장할 수 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OurApiResponse<>("success", dto, null));
    }
}
