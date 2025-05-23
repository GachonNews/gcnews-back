package com.yourorg.article.port.in.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import java.util.List;
import java.util.Optional;

public interface ArticleQueryApiPort {
    List<ArticleResponse> articleCategoryRequest(String category);
    List<ArticleResponse> articleSubCategoryRequest(String subcategory);

    // 오늘 전체 뉴스 중 조회수 1위
    Optional<ArticleResponse> findTodayTopArticle();

    // 오늘 특정 카테고리 뉴스 중 조회수 1위
    Optional<ArticleResponse> findTodayTopArticleByCategory(String category);
}
