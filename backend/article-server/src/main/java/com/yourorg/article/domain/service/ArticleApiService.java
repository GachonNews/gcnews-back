package com.yourorg.article.domain.service;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.persistence.ArticleReadPort;
import com.yourorg.article.port.out.web.ArticleDeliveryPort;
import com.yourorg.article.port.in.web.ArticleApiPort;
import com.yourorg.article.adapter.out.repository.ArticleJPARepository;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleApiService implements ArticleApiPort {

    private final ArticleDeliveryPort articleDeliveryPort;  // ✅ 아웃포트 주입
    private final ArticleReadPort articleReadPort;  // ✅ 아웃포트 주입
    private final ArticleJPARepository articleRepository;

    @Override
    public void ArticleRequest(Long articleId) {
        System.out.println("📥 뉴스 요청: " + articleId);

        Optional<Article> crawling = articleRepository.findByCrawlingId(articleId);
        Long crawlingId = crawling.get().getCrawlingId();

        // 1. 뉴스 ID로 요약 정보 조회
        Optional<Article> articleOptional = articleReadPort.ArticleRequest(crawlingId);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            // 2. 요약 정보 전달
            articleDeliveryPort.deliverArticle(article);
        } else {
            // 3. 요약 정보가 없는 경우 처리
            System.out.println("No article found for article ID: " + articleId);
        }
    }
}
