// KafkaConsumerAdapter.java (메시지 수신 역할)
package com.yourorg.news.adapter.in.message;

import com.yourorg.news.adapter.in.message.dto.NewsResponseDto;
import com.yourorg.news.domain.entity.News;
import com.yourorg.news.port.in.message.NewsJobPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class NewsKafkaAdapter {

    private final ObjectMapper objectMapper;
    private final NewsJobPort newsJobPort; // ✅ 포트 구현체 주입

    public NewsKafkaAdapter(ObjectMapper objectMapper, NewsJobPort newsJobPort) {
        this.objectMapper = objectMapper;
        this.newsJobPort = newsJobPort;
    }

    @KafkaListener(topics = "news-topic", groupId = "news-group")
    public void receiveMessage(String rawMessage) {
        try {
            // ✅ 메시지 → DTO 역직렬화
            NewsResponseDto dto = objectMapper.readValue(rawMessage, NewsResponseDto.class);
            // ✅ DTO → 도메인 객체 변환
            News news = new News(dto.getNewsId(), dto.getContent(), null);
            
            System.out.println("📥 수신된 요약: {}"+ news.getNewsId() + " " + news.getContent());
            
            // ✅ 포트 인터페이스 호출 (비즈니스 로직 실행)
            newsJobPort.requestNewsJob(news); // ✅ 인터페이스 메서드 호출
            
        } catch (Exception e) {
            log.error("메시지 처리 실패: {}", e.getMessage());
        }
    }
}
