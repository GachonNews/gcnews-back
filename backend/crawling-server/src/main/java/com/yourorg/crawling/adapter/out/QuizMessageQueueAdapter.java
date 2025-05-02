package com.yourorg.crawling.adapter.out;

import com.yourorg.crawling.domain.News;
import com.yourorg.crawling.port.out.QuizRequestPort;
import org.springframework.stereotype.Component;

@Component
public class QuizMessageQueueAdapter implements QuizRequestPort {
    @Override
    public void requestQuiz(News news) {
        // 실제로는 MQ, Kafka, RabbitMQ 사용
        System.out.println("[퀴즈 요청 MQ] " + news.getContent());
    }
}