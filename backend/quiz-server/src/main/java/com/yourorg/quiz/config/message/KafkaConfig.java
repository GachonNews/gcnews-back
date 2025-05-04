package com.yourorg.quiz.config.message;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.yourorg.quiz.adapter.in.message.dto.QuizResponseDto;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, QuizResponseDto> consumerFactory() {
        // 1. 명시적 DTO 타입 지정
        JsonDeserializer<QuizResponseDto> deserializer = 
            new JsonDeserializer<>(QuizResponseDto.class);
        
        deserializer.addTrustedPackages("com.yourorg.*");

        // 2. 필수 설정만 추가
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "quiz-group");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 3. ErrorHandlingDeserializer로 래핑
        ErrorHandlingDeserializer<QuizResponseDto> errorHandlingDeserializer = 
            new ErrorHandlingDeserializer<>(deserializer);

        return new DefaultKafkaConsumerFactory<>(
            configs,
            new StringDeserializer(),
            errorHandlingDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, QuizResponseDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, QuizResponseDto> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
}
