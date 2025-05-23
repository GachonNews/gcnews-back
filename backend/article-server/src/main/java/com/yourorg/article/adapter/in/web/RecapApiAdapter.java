package com.yourorg.article.adapter.in.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.adapter.in.web.dto.response.OurApiResponse;
import com.yourorg.article.port.in.web.RecapApiPort;
import com.yourorg.article.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/article/recap")
@RequiredArgsConstructor
public class RecapApiAdapter {

    private final RecapApiPort recapApiPort;

    @Value("${jwt.secret}")
    private String secretKey;

    @Operation(summary = "사용자 월별 리캡 데이터 조회",
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "리캡 있음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "리캡 성공 예시",
                    value = """
                        {
                        "status": "success",
                        "data": {
                            [
                                {
                                    "crawlingId": 1,
                                    "title": "KIEP, 세계경제 성장률 2.7% 전망…美中 관세유예는 \"정상화되는 과정\"",
                                    "category": "economy",
                                    "content": "...",
                                    "articleLink": "https://www.hankyung.com/article/202505139360i",
                                    "imgLink": "https://img.hankyung.com/photo/202505/01.40461532.3.jpg",
                                    "uploadAt": "2025-05-13T14:05"
                                    "views" : 0
                                }
                            ]
                        },
                        "message": "전송 완료"
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
        ),
        @ApiResponse(
            responseCode = "404", description = "해당 월의 리캡 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "리캡 없음 예시",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "해당 월의 리캡 기록이 없습니다."
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{yearMonth}")
    public ResponseEntity<OurApiResponse<List<ArticleResponse>>> getMonthlyRecap(
            @RequestHeader("Authorization") String token,
            @PathVariable String yearMonth) {

        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);
        List<ArticleResponse> response = recapApiPort.getMonthlyRecap(Long.valueOf(userId), yearMonth);
        if (response == null) {
            return ResponseEntity.status(404)
                .body(new OurApiResponse<>("fail", null, "해당 월의 리캡 기록이 없습니다."));
        }
        return ResponseEntity.ok(
            new OurApiResponse<>("success", response, null)
        );
    }
}
