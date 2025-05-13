// ArticleLikeApiAdapter.java
package com.yourorg.article.adapter.in.web;

import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
import com.yourorg.article.port.in.web.ArticleLikeApiPort;
import com.yourorg.article.adapter.in.web.dto.ErrorResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/article/like")
@RequiredArgsConstructor
public class ArticleLikeApiAdapter {

    private final ArticleLikeApiPort articleLikeApiPort;

    @Operation(summary = "좋아요 추가")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "409",
            description = "중복 좋아요",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<?> addLike(
        @RequestParam Long userId,
        @RequestParam Long crawlingId
    ) {
        articleLikeApiPort.addLike(userId, crawlingId);
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "좋아요 추가 완료"
        ));
    }


    @Operation(summary = "좋아요 취소")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "404",
            description = "좋아요 기록 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @DeleteMapping
    public ResponseEntity<?> removeLike(
        @RequestParam Long userId,
        @RequestParam Long crawlingId
    ) {
        articleLikeApiPort.removeLike(userId, crawlingId);
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "좋아요 취소 완료"
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ArticleResponse>> getUserLikes(@PathVariable Long userId) {
        List<ArticleResponse> articles = articleLikeApiPort.getUserLikes(userId);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(articles);
    }
}
