package com.yourorg.article.adapter.out.repository;

import com.yourorg.article.domain.entity.Article;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleJPARepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryInOrderByUploadAtDesc(List<String> category);
    List<Article> findTop5ByCategoryOrderByUploadAtDesc(String category);
    List<Article> findByCrawlingIdIn(List<Long> crawlingIds);
    List<Article> findByCrawlingId(Long crawlingId);
    boolean existsByCrawlingId(Long crawlingId);

    @Modifying
    @Transactional 
    @Query("UPDATE Article a SET a.views = a.views + 1 WHERE a.crawlingId = :crawlingId")
    void incrementViewCount(@Param("crawlingId") Long crawlingId);
}