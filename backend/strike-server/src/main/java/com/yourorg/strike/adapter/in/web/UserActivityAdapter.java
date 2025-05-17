package com.yourorg.strike.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yourorg.strike.adapter.in.dto.DeliveryStrikeDto;
import com.yourorg.strike.adapter.in.dto.OurApiResponse;
import com.yourorg.strike.port.in.web.UserActivityPort;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/recent")
@RequiredArgsConstructor
public class UserActivityAdapter {

    private final UserActivityPort service;

    @Operation(summary = "유저 최근 활동(출석) 등록/수정")
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
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "유저 정보 저장 실패 등 유효하지 않은 요청",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "FailExample",
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
    public ResponseEntity<OurApiResponse<DeliveryStrikeDto>> upsertUser(
            @RequestBody DeliveryStrikeDto strikeDto) {
        DeliveryStrikeDto dto = service.upsertUser(strikeDto.getUserId(), strikeDto);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new OurApiResponse<>("fail", null, "유저 정보를 저장할 수 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OurApiResponse<>("success", dto, null));
    }
}
