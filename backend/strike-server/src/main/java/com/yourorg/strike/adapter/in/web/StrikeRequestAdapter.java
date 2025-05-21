package com.yourorg.strike.adapter.in.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import com.yourorg.strike.adapter.in.dto.DeliveryStrikeDto;
import com.yourorg.strike.adapter.in.dto.OurApiResponse;
import com.yourorg.strike.port.in.web.StrikeRequestPort;
import com.yourorg.strike.util.JwtUtil;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/user-info/attendance")
@RequiredArgsConstructor
public class StrikeRequestAdapter {

    private final StrikeRequestPort service;

    @Value("${jwt.secret}")
    private String secretKey;

    @Operation(summary = "유저 월별 출석 데이터 조회",
    security = @SecurityRequirement(name = "bearerAuth") )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "출석 데이터가 존재하거나, 데이터 없음(빈 배열 + 실패 메시지)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = {
                    @ExampleObject(
                        name = "성공",
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
                    ),
                    @ExampleObject(
                        name = "데이터 없음",
                        value = """
                        {
                          "status": "fail",
                          "data": [],
                          "message": "출석 데이터가 없습니다."
                        }
                        """
                    )
                }
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
    @GetMapping
    public ResponseEntity<OurApiResponse<List<DeliveryStrikeDto>>> getMonthlyStrikes(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        List<DeliveryStrikeDto> strikes = service.getMonthlyStrikes(Long.valueOf(userId), year, month);

        if (strikes == null || strikes.isEmpty()) {
            return ResponseEntity.ok(
                new OurApiResponse<>("fail", List.of(), "출석 데이터가 없습니다.")
            );
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", strikes, null)
        );
    }

    @Operation(summary = "친구 월별 출석 데이터 조회",
    security = @SecurityRequirement(name = "bearerAuth") )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "친구 출석 데이터가 존재하거나, 데이터 없음(빈 배열 + 실패 메시지)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = {
                    @ExampleObject(
                        name = "성공",
                        value = """
                        {
                          "status": "success",
                          "data": [
                            {
                              "date": "2025-06-01",
                              "activity": "출석"
                            }
                          ],
                          "message": null
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "데이터 없음",
                        value = """
                        {
                          "status": "fail",
                          "data": [],
                          "message": "친구의 출석 데이터가 없습니다."
                        }
                        """
                    )
                }
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
    @GetMapping("/{friend_id}")
    public ResponseEntity<OurApiResponse<List<DeliveryStrikeDto>>> getFriendMonthlyStrikes(
            @RequestHeader("Authorization") String token,
            @PathVariable("friend_id") Long friendId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        List<DeliveryStrikeDto> strikes = service.getFriendMonthlyStrikes(friendId, year, month);

        if (strikes == null || strikes.isEmpty()) {
            return ResponseEntity.ok(
                new OurApiResponse<>("fail", List.of(), "친구의 출석 데이터가 없습니다.")
            );
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", strikes, null)
        );
    }
}
