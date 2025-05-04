package com.yourorg.article.port.out.persistence;
import com.yourorg.article.domain.entity.Article;
public interface ArticleSavePort {
    void saveArticle(Article article);
}
