// package com.yourorg.quiz.adapter.out.web;

// import com.yourorg.quiz.adapter.out.web.dto.ArticleDeliveryDto;
// import com.yourorg.quiz.domain.entity.Article;
// import com.yourorg.quiz.port.out.web.ArticleDeliveryPort;
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
//     public void deliverArticle(Article quiz) {
//         // ✅ Domain → Gateway DTO 변환
//         ArticleDeliveryDto dto = new ArticleDeliveryDto(
//             quiz.getArticleId(),
//             quiz.getArticleContent()
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
package com.yourorg.quiz.adapter.out.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.web.QuizDeliveryPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizDeliveryAdapter implements QuizDeliveryPort {

    @Override
    public void deliverQuiz(Quiz quiz) {
        // Domain → DTO 변환
        Quiz dto = new Quiz(
            quiz.getCrawlingId(),
            quiz.getQuizContent(),
            quiz.getQuizAnswer()
        );
        
        // 로깅 최적화
        log.info("""
            =================================================
            ✅ 요약 정보 전송 (게이트웨이 연결 대기 중)
            📌 뉴스 ID: {}
            📌 퀴즈 내용: {}
            📌 답: {}
            =================================================
            """, 
            dto.getCrawlingId(),
            dto.getQuizContent(),
            dto.getQuizAnswer()
        );
    }
}

