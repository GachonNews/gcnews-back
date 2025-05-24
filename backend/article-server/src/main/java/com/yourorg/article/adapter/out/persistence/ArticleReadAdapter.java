package com.yourorg.article.adapter.out.persistence;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.adapter.out.repository.ArticleJPARepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleReadAdapter implements ArticleFindPort {

    private final ArticleJPARepository articleRepository;

    public ArticleReadAdapter(ArticleJPARepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findByCrawlingId(Long crawlingId) {
        return articleRepository.findByCrawlingId(crawlingId);
    }

    @Override
    public List<Article> findByCrawlingIdIn(List<Long> crawlingIds) {
        return articleRepository.findByCrawlingIdIn(crawlingIds);
    }

    @Override
    public List<Article> findTop6ByCategoryOrderByUploadAtDesc(String category) {
        return articleRepository.findTop3ByCategoryOrderByUploadAtDesc(category);
    }

    @Override
    public List<Article> findTop6BySubCategoryOrderByUploadAtDesc(String subCategory) {
        return articleRepository.findTop3BySubCategoryOrderByUploadAtDesc(subCategory);
    }

    @Override
    public boolean existsByCrawlingId(Long crawlingId) {
        return articleRepository.existsByCrawlingId(crawlingId);
    }

    @Override
    public List<Article> findTop5LikedByUser(Long userId, String yearMonth) {
        return articleRepository.findLikedTop6(userId, yearMonth);
    }

    // === [추가] 오늘 전체 조회수 TOP 1 ===
    @Override
    public Optional<Article> findTopByUploadAtTodayOrderByViewsDesc() {
        String today = LocalDate.now().toString();  // ex) "2025-05-23"
        return articleRepository.findTopByUploadAtTodayOrderByViewsDesc(today);
    }

    @Override
    public Optional<Article> findTopByCategoryAndUploadAtTodayOrderByViewsDesc(String category) {
        String today = LocalDate.now().toString();  // ex) "2025-05-23"
        return articleRepository.findTopByCategoryAndUploadAtTodayOrderByViewsDesc(category, today);
    }
}
