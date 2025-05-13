package com.yourorg.article.adapter.in.web;

import com.yourorg.article.port.in.web.ArticleViewPort;
import com.yourorg.article.adapter.in.web.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article/view")
@RequiredArgsConstructor
public class ArticleViewApiAdapter {

    private final ArticleViewPort articleViewPort;

    @Operation(summary = "기사 조회수 증가")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "404",
            description = "기사 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "중복 조회",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<?> addView(@RequestParam Long userId, @RequestParam Long crawlingId) {
        articleViewPort.addView(userId, crawlingId);
        return ResponseEntity.ok().body("조회수 증가 완료");
    }
}
