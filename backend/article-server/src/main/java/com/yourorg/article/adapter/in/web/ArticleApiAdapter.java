package com.yourorg.article.adapter.in.web;

import com.yourorg.article.port.in.web.ArticleApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class ArticleApiAdapter {

    private final ArticleApiPort articleApiPort;

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable Long articleId) {
        articleApiPort.ArticleRequest(articleId);
        return "조회가 시작되었습니다.";
    }
}