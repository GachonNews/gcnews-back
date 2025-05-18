package com.yourorg.user_info.adapter.in.web;

import com.yourorg.user_info.adapter.in.dto.response.FriendResponseDto;
import com.yourorg.user_info.adapter.in.dto.response.OurApiResponse;
import com.yourorg.user_info.port.in.web.FriendRequestPort;
import com.yourorg.user_info.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.util.List;

@RestController
@RequestMapping("/api/user-info/friend")
@RequiredArgsConstructor
public class FriendRequestAdapter {

    @Value("${jwt.secret}")
    private String secretKey;

    private final FriendRequestPort service;

    @Operation(summary = "친구 목록 조회")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "친구 목록 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "친구 목록 성공",
                    value = """
                    {
                        "status": "success",
                        "data": [
                            {
                                "friendId": 2
                            }
                        ],
                        "message": null
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "204", description = "친구 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "친구 없음",
                    value = """
                    {
                        "status": "success",
                        "data": [],
                        "message": "친구가 없습니다."
                    }
                    """
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<OurApiResponse<List<FriendResponseDto>>> getFriends(
            @RequestHeader("Authorization") String token) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        List<FriendResponseDto> list = service.getFriends(Long.valueOf(userId));
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new OurApiResponse<>("success", List.of(), "친구가 없습니다."));
        }
        return ResponseEntity.ok(new OurApiResponse<>("success", list, null));
    }

    @Operation(summary = "친구 추가")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", description = "친구 추가 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "친구 추가 성공",
                    value = """
                    {
                        "status": "success",
                        "data": {
                            "friendId": 2
                        },
                        "message": null
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "친구 추가 실패",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "친구 추가 실패",
                    value = """
                    {
                        "status": "fail",
                        "data": null,
                        "message": "친구 추가에 실패했습니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<OurApiResponse<FriendResponseDto>> addFriend(
            @RequestHeader("Authorization") String token,
            @RequestBody FriendResponseDto friendDto) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        FriendResponseDto dto = service.addFriend(Long.valueOf(userId), friendDto);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new OurApiResponse<>("fail", null, "친구 추가에 실패했습니다."));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OurApiResponse<>("success", dto, null));
    }

    @Operation(summary = "친구 삭제")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "친구 삭제 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "친구 삭제 성공",
                    value = """
                    {
                        "status": "success",
                        "data": {
                            "friendId": 2
                        },
                        "message": null
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "친구 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "친구 없음",
                    value = """
                    {
                        "status": "fail",
                        "data": null,
                        "message": "해당 친구를 찾을 수 없습니다."
                    }
                    """
                )
            )
        )
    })
    @DeleteMapping("/{friend_id}")
    public ResponseEntity<OurApiResponse<FriendResponseDto>> deleteFriend(
            @RequestHeader("Authorization") String token,
            @PathVariable("friend_id") Long friendId) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        FriendResponseDto dto = service.deleteFriend(Long.valueOf(userId), friendId);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new OurApiResponse<>("fail", null, "해당 친구를 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(new OurApiResponse<>("success", dto, null));
    }
}
