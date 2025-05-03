// package com.yourorg.news.adapter.out.web;

// import com.yourorg.news.adapter.out.web.dto.NewsDeliveryDto;
// import com.yourorg.news.domain.entity.News;
// import com.yourorg.news.port.out.web.NewsDeliveryPort;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.*;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

// @Component
// @RequiredArgsConstructor
// public class NewsDeliveryAdapter implements NewsDeliveryPort {

//     private final RestTemplate restTemplate;

//     @Value("${gateway.api.url}")
//     private String gatewayUrl;

//     @Override
//     public void deliverNews(News news) {
//         // ✅ Domain → Gateway DTO 변환
//         NewsDeliveryDto dto = new NewsDeliveryDto(
//             news.getNewsId(),
//             news.getNewsContent()
//         );

//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         headers.setBearerAuth("gateway-token");

//         HttpEntity<NewsDeliveryDto> request = new HttpEntity<>(dto, headers);

//         // ✅ 타입 안정성 위해 ResponseEntity<Void> 사용
//         ResponseEntity<Void> response = restTemplate.postForEntity(
//             gatewayUrl, request, Void.class
//         );

//         if (!response.getStatusCode().is2xxSuccessful()) {
//             throw new RuntimeException("Gateway 전송 실패: " + response.getStatusCode());
//         }
//     }
// }
package com.yourorg.news.adapter.out.web;

import com.yourorg.news.domain.entity.News;
import com.yourorg.news.port.out.web.NewsDeliveryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsDeliveryAdapter implements NewsDeliveryPort {

    @Override
    public void deliverNews(News news) {
        // Domain → DTO 변환
        News dto = new News(
            news.getNewsId(),
            news.getContent(),
            news.getNewsContent() // content 필드에 매핑
        );
        
        // 로깅 최적화
        log.info("""
            =================================================
            ✅ 요약 정보 전송 (게이트웨이 연결 대기 중)
            📌 뉴스 ID: {}
            📌 내용: {}
            📌 요약 내용: {}
            =================================================
            """, 
            dto.getNewsId(), 
            dto.getContent(),
            dto.getNewsContent() // getNews() → getContent() 수정
        );
    }
}

