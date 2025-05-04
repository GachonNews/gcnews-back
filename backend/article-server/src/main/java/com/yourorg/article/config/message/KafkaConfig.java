package com.yourorg.article.config.message;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import com.yourorg.article.adapter.in.message.dto.ArticleResponseDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, ArticleResponseDto> consumerFactory() {
        // 1. 명시적 DTO 타입 지정
        JsonDeserializer<ArticleResponseDto> deserializer = 
            new JsonDeserializer<>(ArticleResponseDto.class);
        
        deserializer.addTrustedPackages("com.yourorg.*");

        // 2. 필수 설정만 추가
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "article-group");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 3. ErrorHandlingDeserializer로 래핑
        ErrorHandlingDeserializer<ArticleResponseDto> errorHandlingDeserializer = 
            new ErrorHandlingDeserializer<>(deserializer);

        return new DefaultKafkaConsumerFactory<>(
            configs,
            new StringDeserializer(),
            errorHandlingDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ArticleResponseDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ArticleResponseDto> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
}
