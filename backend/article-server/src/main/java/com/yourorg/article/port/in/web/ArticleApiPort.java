package com.yourorg.article.port.in.web;

public interface ArticleApiPort {
    void ArticleRequest(Long articleId);  // ✅ 컨트롤러 역할
}
