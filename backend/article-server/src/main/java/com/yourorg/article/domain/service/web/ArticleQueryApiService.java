package com.yourorg.article.domain.service.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.in.web.ArticleQueryApiPort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.domain.service.exceptionhandler.QueryException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
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

    // 1. 모든 카테고리 기사 조회 (그룹화)
    @Override
    public List<ArticleResponse> articleAllRequest() {
        List<Article> articles = articleFindPort.findByCategoryInOrderByUploadAtDesc(ALLOWED_CATEGORIES);

        Map<String, List<Article>> grouped = articles.stream()
            .collect(Collectors.groupingBy(
                Article::getCategory,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> list.stream()
                        .limit(5) // 상위 5개만 선택
                        .collect(Collectors.toList())
                )
            ));

        return grouped.values().stream()
            .flatMap(List::stream)
            .map(ArticleResponse::fromEntity)
            .collect(Collectors.toList());
    }

    // 2. 특정 카테고리 기사 조회
    @Override
    public List<ArticleResponse> articleCategoryRequest(String category) {
        validateCategory(category);

        List<Article> articles = articleFindPort
            .findTop5ByCategoryOrderByUploadAtDesc(category);

        validateEmptyResult(articles, category);

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