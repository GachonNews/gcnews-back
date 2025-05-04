package com.yourorg.article.port.out.web;
import com.yourorg.article.domain.entity.Article;

public interface ArticleDeliveryPort {
    void deliverArticle(Article article);
}
