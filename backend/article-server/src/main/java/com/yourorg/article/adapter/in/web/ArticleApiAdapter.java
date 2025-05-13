package com.yourorg.article.adapter.in.web;

import com.yourorg.article.port.in.web.ArticleQueryApiPort;
import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
import com.yourorg.article.adapter.in.web.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleApiAdapter {

    private final ArticleQueryApiPort articleQueryApiPort;

    @Operation(summary = "카테고리별 기사 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "404",
            description = "카테고리 없음/기사 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "유효하지 않은 카테고리",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @GetMapping("/{category}")
    public ResponseEntity<List<ArticleResponse>> getCategoryArticle(@PathVariable String category) {
        List<ArticleResponse> articles = articleQueryApiPort.articleCategoryRequest(category);
        return ResponseEntity.ok().body(articles);
    }
}
