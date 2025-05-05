package com.yourorg.article.port.out.persistence;

import com.yourorg.article.domain.entity.Article;
import java.util.List;

public interface ArticleFindPort {
    List<Article> findByCategoryInOrderByUploadAtDesc(List<String> category);
    List<Article> findTop5ByCategoryOrderByUploadAtDesc(String category);
    List<Article> findByCrawlingIdIn(List<Long> crawlingIds);
    List<Article> findByCrawlingId(Long crawlingId);
    boolean existsByCrawlingId(Long crawlingId);
}