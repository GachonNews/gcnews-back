package com.yourorg.article.adapter.out.persistence;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.yourorg.article.adapter.out.repository.ArticleJPARepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MySQLArticleReadAdapter implements ArticleReadPort {

    private final ArticleJPARepository articleRepository;

    @Override
    public Optional<Article> ArticleRequest(Long articleId) { // ✅ Optional 반환
        System.out.println("📥 전달 요청: " + articleId);
        return articleRepository.findById(articleId); // ✅ findByArticleId 메서드 사용
    }
}
