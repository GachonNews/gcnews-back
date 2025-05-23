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
    public List<Article> findTop6ByCategoryOrderByUploadAtDesc(String category) {
        return articleRepository.findTop6ByCategoryOrderByUploadAtDesc(category);
    }

    @Override
    public List<Article> findTop6BySubCategoryOrderByUploadAtDesc(String subCategory) {
        return articleRepository.findTop6BySubCategoryOrderByUploadAtDesc(subCategory);
    }

    @Override
    public boolean existsByCrawlingId(Long crawlingId) {
        return articleRepository.existsByCrawlingId(crawlingId);
    }
    @Override
    public List<Article> findTop5LikedByUser(Long userId, String yearMonth) {
        return articleRepository.findLikedTop6(userId, yearMonth); // JPA 커스텀 쿼리
    }
}
