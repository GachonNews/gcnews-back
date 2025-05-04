package com.yourorg.article.domain.service;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleSavePort;
import com.yourorg.article.port.in.message.ArticleJobPort;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleJobService implements ArticleJobPort {
    
    private final ArticleSavePort articleSavePort;

    @Override
    public void requestArticleJob(Article article) { // ✅ 인터페이스 메서드 정확히 재정의
        articleSavePort.saveArticle(article); // ✅ void 반환값 처리
    }
}
