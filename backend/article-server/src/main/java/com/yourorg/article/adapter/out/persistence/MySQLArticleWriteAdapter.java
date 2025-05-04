package com.yourorg.article.adapter.out.persistence;

import com.yourorg.article.adapter.out.repository.ArticleJPARepository;
import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleSavePort;

import org.springframework.stereotype.Component;

@Component
public class MySQLArticleWriteAdapter implements ArticleSavePort {
    
    private final ArticleJPARepository articleRepository;
    
    public MySQLArticleWriteAdapter(ArticleJPARepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    
    @Override
    public void saveArticle(Article article) {
        articleRepository.save(article);
    }
}