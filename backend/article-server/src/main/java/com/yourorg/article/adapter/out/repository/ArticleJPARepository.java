package com.yourorg.article.adapter.out.repository;

import com.yourorg.article.domain.entity.Article;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJPARepository extends JpaRepository<Article, Long> {
    Optional<Article> findByCrawlingId(Long articleId);
}