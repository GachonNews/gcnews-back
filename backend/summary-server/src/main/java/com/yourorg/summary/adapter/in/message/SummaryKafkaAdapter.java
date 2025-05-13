package com.yourorg.summary.adapter.in.message;

import com.yourorg.summary.adapter.in.message.dto.SummaryConsumerDto;
import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.in.message.SummaryJobPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SummaryKafkaAdapter {

    private final ObjectMapper objectMapper;
    private final SummaryJobPort summaryJobPort;

    public SummaryKafkaAdapter(ObjectMapper objectMapper, SummaryJobPort summaryJobPort) {
        this.objectMapper = objectMapper;
        this.summaryJobPort = summaryJobPort;
    }

    @KafkaListener(topics = "summary-topic", groupId = "summary-group")
    public void receiveMessage(String rawMessage) {
        try {
            // 메시지 → DTO 역직렬화
            SummaryConsumerDto dto = objectMapper.readValue(rawMessage, SummaryConsumerDto.class);

            // content가 없으면 처리하지 않음
            if (dto.getContent() == null || dto.getContent().isBlank()) {
                log.warn("빈 메시지 수신: crawlingId={}", dto.getCrawlingId());
                return;
            }

            // DTO → 도메인 객체 변환 (summaryContent는 서비스에서 채워줌)
            Summary summary = new Summary(
                dto.getCrawlingId(),
                dto.getContent(),
                ""   // null 대신 빈 문자열로 초기화
            );

            log.info("요약 메시지 수신: crawlingId={}, content 길이={}", 
                summary.getCrawlingId(), summary.getContent().length());

            // 비즈니스 로직 실행 (요약 생성 & 저장)
            summaryJobPort.requestSummaryJob(summary);

        } catch (Exception e) {
            log.error("요약 메시지 처리 중 오류 발생: {}", e.getMessage(), e);
        }
    }
}
