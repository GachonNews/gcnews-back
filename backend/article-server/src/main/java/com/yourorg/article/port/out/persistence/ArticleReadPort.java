package com.yourorg.article.port.out.persistence;
import com.yourorg.article.domain.entity.Article;
import java.util.Optional;

public interface ArticleReadPort {
    Optional<Article> ArticleRequest(Long articleId); // "load" → "find"로 변경
}