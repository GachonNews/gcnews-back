package com.yourorg.crawling.adapter.out.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourorg.crawling.adapter.out.message.dto.SummaryRequestDto;
import com.yourorg.crawling.port.out.message.SummaryRequestPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Slf4j
@Component
public class SummaryKafkaAdapter implements SummaryRequestPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "summary-topic";

    public SummaryKafkaAdapter(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void requestSummary(SummaryRequestDto dto) {
        try {
            String key = UUID.randomUUID().toString();
            String payload = objectMapper.writeValueAsString(dto);
            
            kafkaTemplate.send(TOPIC, key, payload)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("✅ [Kafka] summary 전송 성공 | Key: {}, Partition: {}",
                                key, result.getRecordMetadata().partition());
                    } else {
                        log.error("❌ [Kafka] summary 전송 실패 | Key: {} | Error: {}",
                                key, ex.getMessage());
                    }
                });
            
        } catch (Exception e) {
            log.error("메시지 처리 실패: {}", e.getMessage());
        }
    }
}
