package com.yourorg.quiz.adapter.in.web;

import com.yourorg.quiz.adapter.in.web.dto.QuizResponseDto;
import com.yourorg.quiz.adapter.in.web.dto.OurApiResponse;
import com.yourorg.quiz.port.in.web.QuizApiPort;
import com.yourorg.quiz.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizApiAdapter {

    private final QuizApiPort quizApiPort;

    @Value("${jwt.secret}")
    private String secretKey;

    @Operation(summary = "퀴즈 단건 조회",
    security = @SecurityRequirement(name = "bearerAuth") )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "퀴즈 조회 성공",
                    value = """
                    {
                    "status": "success",
                    "data": {
                        "crawlingId": 1,
                        "quizContent": "게티이미지뱅크 대외경제정책연구원(KIEP)은 올해 세계 경제 성장률 전망치를 지난해 11월 전망치보다 높게 제시했다.",
                        "quizAnswer": false
                    },
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
        ),
        @ApiResponse(
            responseCode = "404",
            description = "퀴즈를 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "퀴즈 조회 실패",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "퀴즈를 찾을 수 없습니다: 12345"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{crawlingId}")
    public ResponseEntity<OurApiResponse<QuizResponseDto>> getQuizByCrawlingId(
            @RequestHeader("Authorization") String token,
            @PathVariable Long crawlingId) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);

        return quizApiPort.getQuizByCrawlingId(crawlingId)
            .map(dto ->
                ResponseEntity.ok(
                    new OurApiResponse<>("success", dto, null)
                )
            )
            .orElseGet(() ->
                ResponseEntity.status(404)
                    .body(new OurApiResponse<>("fail", null, "퀴즈를 찾을 수 없습니다: " + crawlingId))
            );
    }
}
