package com.yourorg.article.domain.service.message;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.in.message.ArticleJobPort;
import com.yourorg.article.port.out.persistence.ArticleWritePort;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleJobService implements ArticleJobPort {
    
    private final ArticleWritePort articleJobWrite;

    @Override
    public void responseArticleJob(Article article) { // ✅ 인터페이스 메서드 정확히 재정의
        articleJobWrite.writeArticle(article); // ✅ void 반환값 처리
    }
}