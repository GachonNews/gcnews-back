package com.yourorg.quiz.adapter.in.web;

import com.yourorg.quiz.adapter.in.web.dto.QuizResponseDto;
import com.yourorg.quiz.port.in.web.QuizApiPort;
import com.yourorg.quiz.adapter.in.web.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizApiAdapter {

    private final QuizApiPort quizApiPort;

    @Operation(summary = "퀴즈 단건 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "404",
            description = "퀴즈를 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @GetMapping("/{crawlingId}")
    public ResponseEntity<QuizResponseDto> getQuizByCrawlingId(@PathVariable Long crawlingId) {
        QuizResponseDto dto = quizApiPort
            .getQuizByCrawlingId(crawlingId)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "퀴즈를 찾을 수 없습니다: " + crawlingId
                )
            );

        return ResponseEntity
            .ok()
            .body(dto);
    }
}
