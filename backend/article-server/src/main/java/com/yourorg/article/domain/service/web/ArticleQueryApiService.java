package com.yourorg.article.domain.service.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.in.web.ArticleQueryApiPort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.domain.service.exceptionhandler.QueryException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleQueryApiService implements ArticleQueryApiPort {

    private final ArticleFindPort articleFindPort;

    // 허용 카테고리 정의 (환경 변수로 분리 가능)
    private static final List<String> ALLOWED_CATEGORIES = List.of(
        "economy", "financial-market", "industry",
        "distribution", "it", "international"
    );

    // 1. 오늘 전체 뉴스 중 조회수 1위
    @Override
    public Optional<ArticleResponse> findTodayTopArticle() {
        return articleFindPort.findTopByUploadAtTodayOrderByViewsDesc()
                .map(ArticleResponse::fromEntity);
    }

    // 2. 오늘 카테고리별 뉴스 중 조회수 1위
    @Override
    public Optional<ArticleResponse> findTodayTopArticleByCategory(String category) {
        validateCategory(category);
        return articleFindPort.findTopByCategoryAndUploadAtTodayOrderByViewsDesc(category)
                .map(ArticleResponse::fromEntity);
    }

    // 3. 특정 카테고리 기사 리스트 (기존)
    @Override
    public List<ArticleResponse> articleCategoryRequest(String category) {
        validateCategory(category);

        List<Article> articles = articleFindPort
                .findTop6ByCategoryOrderByUploadAtDesc(category);

        validateEmptyResult(articles, category);

        return articles.stream()
                .map(ArticleResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 4. 서브카테고리 기사 리스트 (기존)
    @Override
    public List<ArticleResponse> articleSubCategoryRequest(String subcategory) {
        List<Article> articles = articleFindPort
                .findTop6BySubCategoryOrderByUploadAtDesc(subcategory);

        validateEmptyResult(articles, subcategory);

        return articles.stream()
                .map(ArticleResponse::fromEntity)
                .collect(Collectors.toList());
    }

    //-- 검증 메서드들 --//
    private void validateCategory(String category) {
        if (!ALLOWED_CATEGORIES.contains(category)) {
            throw new QueryException.InvalidCategoryException("유효하지 않은 카테고리: " + category);
        }
    }

    private void validateEmptyResult(List<Article> articles, String category) {
        if (articles.isEmpty()) {
            throw new QueryException.CategoryNotFoundException("카테고리 [" + category + "]에 기사가 없습니다");
        }
    }
}
