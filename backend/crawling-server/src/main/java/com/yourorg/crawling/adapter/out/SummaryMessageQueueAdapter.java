package com.yourorg.crawling.adapter.out;

import com.yourorg.crawling.domain.News;
import com.yourorg.crawling.port.out.SummaryRequestPort;
import org.springframework.stereotype.Component;

@Component
public class SummaryMessageQueueAdapter implements SummaryRequestPort {
    @Override
    public void requestSummary(News news) {
        // 실제로는 MQ, Kafka, RabbitMQ 사용
        System.out.println("[요약 요청 MQ] " + news.getContent());
    }
}