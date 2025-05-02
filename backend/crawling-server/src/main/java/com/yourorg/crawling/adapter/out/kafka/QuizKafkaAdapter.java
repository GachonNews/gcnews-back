package com.yourorg.crawling.adapter.out.kafka;

import com.yourorg.crawling.domain.News;
import com.yourorg.crawling.port.out.QuizRequestPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuizKafkaAdapter implements QuizRequestPort {

    private final KafkaTemplate<String, News> kafkaTemplate;
    private static final String TOPIC = "quiz-topic";

    @Autowired
    public QuizKafkaAdapter(KafkaTemplate<String, News> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void requestQuiz(News news) {
        String key = String.valueOf(news.getNewsId()); 
        kafkaTemplate.send(TOPIC, key, news); 
        
        System.out.println("✅ [Kafka] quiz 메시지 전송 완료 | ID: " + key);
    }
}
