package com.yourorg.strike.adapter.in.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import com.yourorg.strike.adapter.in.dto.DeliveryStrikeDto;
import com.yourorg.strike.adapter.in.dto.OurApiResponse;
import com.yourorg.strike.port.in.web.StrikeRequestPort;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/{user_id}/attendance")
@RequiredArgsConstructor
public class StrikeRequestAdapter {

    private final StrikeRequestPort service;

    @Operation(summary = "유저 월별 출석 데이터 조회")
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
                                "userId": 2,
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
        )
    })
    @GetMapping
    public ResponseEntity<OurApiResponse<List<DeliveryStrikeDto>>> getMonthlyStrikes(
            @PathVariable("user_id") Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<DeliveryStrikeDto> strikes = service.getMonthlyStrikes(userId, year, month);

        if (strikes == null || strikes.isEmpty()) {
            return ResponseEntity.ok(
                new OurApiResponse<>("fail", List.of(), "출석 데이터가 없습니다.")
            );
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", strikes, null)
        );
    }

    @Operation(summary = "친구 월별 출석 데이터 조회")
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
                              "userId": 2,
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
        )
    })
    @GetMapping("/{friend_id}")
    public ResponseEntity<OurApiResponse<List<DeliveryStrikeDto>>> getFriendMonthlyStrikes(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
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
