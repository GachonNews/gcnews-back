package com.yourorg.crawling.adapter.out.kafka;

import com.yourorg.crawling.domain.News;
import com.yourorg.crawling.port.out.SummaryRequestPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SummaryKafkaAdapter implements SummaryRequestPort {

    private final KafkaTemplate<String, News> kafkaTemplate;
    private static final String TOPIC = "summary-topic";

    @Autowired
    public SummaryKafkaAdapter(KafkaTemplate<String, News> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void requestSummary(News news) {
        String key = String.valueOf(news.getNewsId());
        kafkaTemplate.send(TOPIC, key, news); 
        
        System.out.println("✅ [Kafka] summary 메시지 전송 완료 | ID: " + key);
    }
}
