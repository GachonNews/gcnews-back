package com.yourorg.article.port.out.persistence;

import com.yourorg.article.domain.entity.Article;
import java.util.List;

import java.util.Optional;

public interface ArticleFindPort {
    Optional<Article> findTopByUploadAtTodayOrderByViewsDesc();
    Optional<Article> findTopByCategoryAndUploadAtTodayOrderByViewsDesc(String category);
    List<Article> findTop6ByCategoryOrderByUploadAtDesc(String category);
    List<Article> findTop6BySubCategoryOrderByUploadAtDesc(String category);
    List<Article> findByCrawlingIdIn(List<Long> crawlingIds);
    List<Article> findByCrawlingId(Long crawlingId);
    boolean existsByCrawlingId(Long crawlingId);
    List<Article> findTop5LikedByUser(Long userId, String yearMonth);
}