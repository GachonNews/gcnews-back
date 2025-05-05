package com.yourorg.article.adapter.out.persistence;

import com.yourorg.article.adapter.out.repository.ArticleJPARepository;
import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleWritePort;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor    
public class ArticleJobWriteAdapter implements ArticleWritePort {
    
    private final ArticleJPARepository articleRepository;
    
    @Override
    public void writeArticle(Article article) {
        articleRepository.save(article);
    }
    @Override
    public void incrementViewCount(Long crawlingId) {
        articleRepository.incrementViewCount(crawlingId);
    }
}