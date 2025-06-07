package com.yourorg.article.adapter.out.repository;

import com.yourorg.article.domain.entity.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleJPARepository extends JpaRepository<Article, Long> {

  List<Article> findTop3ByCategoryOrderByUploadAtDesc(String category);
  List<Article> findTop3BySubCategoryOrderByUploadAtDesc(String subCategory);
  List<Article> findByCrawlingIdIn(List<Long> crawlingIds);
  List<Article> findByCrawlingId(Long crawlingId);
  boolean existsByCrawlingId(Long crawlingId);


  @Modifying
  @Transactional 
  @Query("UPDATE Article a SET a.views = a.views + 1 WHERE a.crawlingId = :crawlingId")
  void incrementViewCount(@Param("crawlingId") Long crawlingId);

  // 오늘 전체 조회수 1위 (uploadAt이 오늘의 00:00~내일 00:00 사이)
  @Query(value = """
      SELECT *
      FROM article a
      WHERE SUBSTRING(a.upload_at, 1, 10) = :today
      ORDER BY a.views DESC
      LIMIT 1
      """, nativeQuery = true)
  Optional<Article> findTopByUploadAtTodayOrderByViewsDesc(@Param("today") String today);


  // 오늘 카테고리별 조회수 1위
  @Query(value = """
    (SELECT *
     FROM article a
     WHERE a.category = :category
       AND SUBSTRING(a.upload_at, 1, 10) = :today
     ORDER BY a.views DESC
     LIMIT 1)
    UNION ALL
    (SELECT *
     FROM article a
     WHERE a.category = :category
       AND SUBSTRING(a.upload_at, 1, 10) <> :today
       AND SUBSTRING(a.upload_at, 1, 10) = (
         SELECT MAX(SUBSTRING(b.upload_at, 1, 10)) FROM article b WHERE b.category = :category
       )
     ORDER BY a.views DESC
     LIMIT 1)
    LIMIT 1
    """, nativeQuery = true)
  Optional<Article> findTopByCategoryAndUploadAtTodayOrderByViewsDesc(
      @Param("category") String category,
      @Param("today") String today
  );


  @Query(value = """
    SELECT a.*,
          CASE WHEN EXISTS (
            SELECT 1 FROM user u
            WHERE u.user_id = :userId AND u.crawling_id = a.crawling_id
          ) THEN a.views + 1000
          ELSE a.views
          END AS weighted_views
    FROM article a
    WHERE SUBSTRING(a.upload_at, 1, 7) = :yearMonth
    ORDER BY
      CASE WHEN (SELECT SUM(a2.views)
                FROM article a2
                WHERE SUBSTRING(a2.upload_at, 1, 7) = :yearMonth) = 0
          THEN a.upload_at
          ELSE
            CASE WHEN EXISTS (
              SELECT 1 FROM user u
              WHERE u.user_id = :userId AND u.crawling_id = a.crawling_id
            ) THEN a.views + 1000
            ELSE a.views
            END
      END DESC
    LIMIT 6
    """, nativeQuery = true)
  List<Article> findLikedTop6(
      @Param("userId") Long userId,
      @Param("yearMonth") String yearMonth
  );
}
