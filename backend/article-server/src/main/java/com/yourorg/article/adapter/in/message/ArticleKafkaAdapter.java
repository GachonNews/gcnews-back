// KafkaConsumerAdapter.java (메시지 수신 역할)
package com.yourorg.article.adapter.in.message;

import com.yourorg.article.adapter.in.message.dto.ArticleResponseDto;
import com.yourorg.article.domain.entity.Article;
import com.yourorg.article.port.in.message.ArticleJobPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ArticleKafkaAdapter {

    private final ObjectMapper objectMapper;
    private final ArticleJobPort articleJobPort; // ✅ 포트 구현체 주입

    public ArticleKafkaAdapter(ObjectMapper objectMapper, ArticleJobPort articleJobPort) {
        this.objectMapper = objectMapper;
        this.articleJobPort = articleJobPort;
    }

    @KafkaListener(topics = "article-topic", groupId = "article-group")
    public void receiveMessage(String rawMessage) {
        try {
            // ✅ 메시지 → DTO 역직렬화
            ArticleResponseDto dto = objectMapper.readValue(rawMessage, ArticleResponseDto.class);
            // ✅ DTO → 도메인 객체 변환
            Article article = new Article(dto.getCrawlingId(), dto.getTitle(), dto.getCategory(), dto.getContent(), dto.getArticleLink(), dto.getImgLink(), dto.getUploadAt());
            
            System.out.println("📥 수신된 요약: {}"+ article.getCrawlingId() + " " + article.getContent());
            
            // ✅ 포트 인터페이스 호출 (비즈니스 로직 실행)
            articleJobPort.requestArticleJob(article); // ✅ 인터페이스 메서드 호출
            
        } catch (Exception e) {
            log.error("메시지 처리 실패: {}", e.getMessage());
        }
    }
}
