package com.yourorg.crawling.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic summaryTopic() {
        return TopicBuilder.name("summary-topic")
                .partitions(3)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic quizTopic() {
        return TopicBuilder.name("quiz-topic")
                .partitions(3)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic articleTopic() {
        return TopicBuilder.name("article-topic")
                .partitions(3)
                .replicas(1)
                .compact()
                .build();
    }
}
