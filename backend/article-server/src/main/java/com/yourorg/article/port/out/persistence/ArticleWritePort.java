package com.yourorg.article.port.out.persistence;
import com.yourorg.article.domain.entity.Article;

public interface ArticleWritePort {
    void writeArticle(Article article);
    void incrementViewCount(Long crawlingId);
}