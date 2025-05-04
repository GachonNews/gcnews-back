// package com.yourorg.article.adapter.out.web;

// import com.yourorg.article.adapter.out.web.dto.ArticleDeliveryDto;
// import com.yourorg.article.domain.entity.Article;
// import com.yourorg.article.port.out.web.ArticleDeliveryPort;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.*;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

// @Component
// @RequiredArgsConstructor
// public class ArticleDeliveryAdapter implements ArticleDeliveryPort {

//     private final RestTemplate restTemplate;

//     @Value("${gateway.api.url}")
//     private String gatewayUrl;

//     @Override
//     public void deliverArticle(Article article) {
//         // ✅ Domain → Gateway DTO 변환
//         ArticleDeliveryDto dto = new ArticleDeliveryDto(
//             article.getArticleId(),
//             article.getArticleContent()
//         );

//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.setBearerAuth("gateway-token");

//         HttpEntity<ArticleDeliveryDto> request = new HttpEntity<>(dto, headers);

//         // ✅ 타입 안정성 위해 ResponseEntity<Void> 사용
//         ResponseEntity<Void> response = restTemplate.postForEntity(
//             gatewayUrl, request, Void.class
//         );

//         if (!response.getStatusCode().is2xxSuccessful()) {
//             throw new RuntimeException("Gateway 전송 실패: " + response.getStatusCode());
//         }
//     }
// }
package com.yourorg.article.adapter.out.web;

import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.out.web.ArticleDeliveryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleDeliveryAdapter implements ArticleDeliveryPort {

    @Override
    public void deliverArticle(Article article) {
        // Domain → DTO 변환
        Article dto = new Article(
            article.getArticleId(),
            article.getTitle(),
            article.getCategory(),
            article.getContent(),
            article.getArticleLink(),  // 수정: getArticlelink() → getArticleLink()
            article.getImgLink(),   // 수정: getImageUrl() → getImgLink()
            article.getUploadAt()
        );
        
        // 로깅 최적화
        log.info("""
            =================================================
            ✅ 요약 정보 전송 (게이트웨이 연결 대기 중)
            📌 뉴스 ID: {}
            📌 제목: {}
            📌 카테고리: {}
            📌 내용: {}
            📌 뉴스 링크: {}
            📌 이미지 링크: {}
            📌 업로드 시간: {}
            =================================================
            """, 
            dto.getArticleId(),
            dto.getTitle(),
            dto.getCategory(),
            dto.getContent(),
            dto.getArticleLink(),   // 수정: getArticlelink() → getArticleLink()
            dto.getImgLink(),    // 수정: getImageUrl() → getImgLink()
            dto.getUploadAt()
        );
    }
}

