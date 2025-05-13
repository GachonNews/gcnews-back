// QuizKafkaAdapter.java (메시지 수신 역할)
package com.yourorg.quiz.adapter.in.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourorg.quiz.adapter.in.message.dto.QuizConsumerDto;
import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.in.message.QuizJobPort;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QuizKafkaAdapter {

    private final ObjectMapper objectMapper;
    private final QuizJobPort quizJobPort;

    public QuizKafkaAdapter(ObjectMapper objectMapper, QuizJobPort quizJobPort) {
        this.objectMapper = objectMapper;
        this.quizJobPort = quizJobPort;
    }

    @KafkaListener(topics = "quiz-topic", groupId = "quiz-group")
    public void receiveMessage(String rawMessage) {
        try {
            // 메시지 → DTO 역직렬화
            QuizConsumerDto dto = objectMapper.readValue(rawMessage, QuizConsumerDto.class);

            // DTO → 도메인 객체 변환 (필요 없는 필드는 null 처리)
            Quiz quiz = new Quiz(
                dto.getCrawlingId(), 
                dto.getContent(), 
                null,   // 예: answer 필드가 있으면 dto.getAnswer() 등으로 교체
                null    // 추가 필드도 필요한 경우 dto에서 받아 넣어 주세요
            );

            log.info("📥 수신된 퀴즈: crawlingId={} content={}", 
                quiz.getCrawlingId(), quiz.getContent()
            );

            // 포트 인터페이스 호출 (비즈니스 로직 실행)
            quizJobPort.requestQuizJob(quiz);

        } catch (Exception e) {
            log.error("퀴즈 메시지 처리 실패: {}", e.getMessage(), e);
        }
    }
}
