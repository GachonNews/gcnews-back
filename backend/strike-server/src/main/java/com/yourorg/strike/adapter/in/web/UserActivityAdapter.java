package com.yourorg.strike.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import com.yourorg.strike.adapter.in.dto.DeliveryStrikeDto;
import com.yourorg.strike.adapter.in.dto.OurApiResponse;
import com.yourorg.strike.port.in.web.UserActivityPort;
import com.yourorg.strike.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/user-info/recent")
@RequiredArgsConstructor
public class UserActivityAdapter {

    private final UserActivityPort service;

    @Value("${jwt.secret}")
    private String secretKey;

    @Operation(summary = "유저 최근 활동(출석) 등록/수정",
    security = @SecurityRequirement(name = "bearerAuth") )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "성공적으로 유저 활동이 등록/수정됨",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "SuccessExample",
                    value = """
                        {
                        "status": "success",
                        "data": [
                            {
                                "newsId": 2,
                                "visitDate": "2025-05-11",
                                "summarized": false,
                                "quized": false
                            }
                        ],
                        "message": null
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "입력값 오류",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "입력값 오류",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "입력값 오류입니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<OurApiResponse<DeliveryStrikeDto>> upsertUser(
            @RequestHeader("Authorization") String token,
            @RequestBody DeliveryStrikeDto strikeDto) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        DeliveryStrikeDto dto = service.upsertUser(Long.valueOf(userId), strikeDto);
        System.out.print("!!!!!!!!!!!!!!!!!!!! " + userId);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new OurApiResponse<>("fail", null, "유저 정보를 저장할 수 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OurApiResponse<>("success", dto, null));
    }
}
