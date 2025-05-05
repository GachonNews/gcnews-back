package com.yourorg.article.port.in.message;

import com.yourorg.article.domain.entity.Article;

public interface ArticleJobPort {
    void responseArticleJob(Article article);
}
