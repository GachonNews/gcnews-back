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

  @Query(value = """
  SELECT a.*
  FROM Article a
  INNER JOIN User ul
      ON a.crawling_id = ul.crawling_id
  WHERE ul.user_id = :userId
    AND SUBSTRING(a.upload_at, 1, 7) = :yearMonth
  ORDER BY a.views DESC
  LIMIT 5
  """, nativeQuery = true)
  List<Article> findLikedTop5(
    @Param("userId") Long userId,
    @Param("yearMonth") String yearMonth
  );

  // 2. 사용자가 좋아요 누르지 않은 + 특정 월 업로드된 기사 Top5
  @Query(value = """
  SELECT a.*
  FROM Article a
  LEFT JOIN User ul
    ON a.crawling_id = ul.crawling_id
    AND ul.user_id = :userId
  WHERE ul.crawling_id IS NULL
    AND SUBSTRING(a.upload_at, 1, 7) = :yearMonth   -- 요 부분만!
  ORDER BY a.views DESC
  LIMIT 5
  """, nativeQuery = true)
  List<Article> findNotLikedTop5(
    @Param("userId") Long userId, 
    @Param("yearMonth") String yearMonth
  );
}