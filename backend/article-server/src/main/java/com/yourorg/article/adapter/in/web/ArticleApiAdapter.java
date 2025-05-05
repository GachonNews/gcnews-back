package com.yourorg.article.adapter.in.web;

import com.yourorg.article.port.in.web.ArticleQueryApiPort;
import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleApiAdapter {

    private final ArticleQueryApiPort articleQueryApiPort;

    // 모든 기사 조회: GET /api/article
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticle() {
        List<ArticleResponse> articles = articleQueryApiPort.articleAllRequest();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(articles);
    }

    // 카테고리별 기사 조회: GET /api/article/{category}
    @GetMapping("/{category}")
    public ResponseEntity<List<ArticleResponse>> getCategoryArticle(@PathVariable String category) {
        List<ArticleResponse> articles = articleQueryApiPort.articleCategoryRequest(category);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(articles);
    }
}
