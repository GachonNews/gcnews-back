// KafkaConsumerAdapter.java (메시지 수신 역할)
package com.yourorg.summary.adapter.in.message;

import com.yourorg.summary.adapter.in.message.dto.SummaryResponseDto;
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
    private final SummaryJobPort summaryJobPort; // ✅ 포트 구현체 주입

    public SummaryKafkaAdapter(ObjectMapper objectMapper, SummaryJobPort summaryJobPort) {
        this.objectMapper = objectMapper;
        this.summaryJobPort = summaryJobPort;
    }

    @KafkaListener(topics = "summary-topic", groupId = "summary-group")
    public void receiveMessage(String rawMessage) {
        try {
            // ✅ 메시지 → DTO 역직렬화
            SummaryResponseDto dto = objectMapper.readValue(rawMessage, SummaryResponseDto.class);
            // ✅ DTO → 도메인 객체 변환
            Summary summary = new Summary(dto.getCrawlingId(), dto.getContent(), null);
            
            System.out.println("📥 수신된 요약: {}"+ summary.getCrawlingId() + " " + summary.getContent());
            
            // ✅ 포트 인터페이스 호출 (비즈니스 로직 실행)
            summaryJobPort.requestSummaryJob(summary); // ✅ 인터페이스 메서드 호출
            
        } catch (Exception e) {
            log.error("메시지 처리 실패: {}", e.getMessage());
        }
    }
}
