// ArticleAdapter.java
package com.yourorg.article.adapter.out.persistence;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.adapter.out.repository.ArticleJPARepository;
import org.springframework.stereotype.Repository;
import java.util.List;

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
    public List<Article> findByCategoryInOrderByUploadAtDesc(List<String> category) {
        return articleRepository.findByCategoryInOrderByUploadAtDesc(category);
    }

    @Override
    public List<Article> findTop5ByCategoryOrderByUploadAtDesc(String category) {
        return articleRepository.findTop5ByCategoryOrderByUploadAtDesc(category);
    }

    @Override
    public boolean existsByCrawlingId(Long crawlingId) {
        return articleRepository.existsByCrawlingId(crawlingId);
    }
    @Override
    public List<Article> findTop5LikedByUser(Long userId, String yearMonth) {
        return articleRepository.findLikedTop5(userId, yearMonth); // JPA 커스텀 쿼리
    }

    @Override
    public List<Article> findTop5NotLikedByUserAndMonth(Long userId, String yearMonth) {
        return articleRepository.findNotLikedTop5(userId, yearMonth);
    }
}
