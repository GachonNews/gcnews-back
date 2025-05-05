package com.yourorg.article.adapter.in.web;

import com.yourorg.article.port.in.web.ArticleViewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleViewAdapter {

    private final ArticleViewPort articleViewPort;

    // 카테고리별 기사 조회: GET /api/article/{category}
    @PostMapping
    public ResponseEntity<?> addView(
        @RequestParam Long userId,
        @RequestParam Long crawlingId
    ) {
        articleViewPort.addView(userId, crawlingId);
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "View 완료"
        ));
    }
}
