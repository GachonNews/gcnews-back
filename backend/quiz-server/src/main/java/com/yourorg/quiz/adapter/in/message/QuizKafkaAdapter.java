// KafkaConsumerAdapter.java (메시지 수신 역할)
package com.yourorg.quiz.adapter.in.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourorg.quiz.adapter.in.message.dto.QuizResponseDto;
import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.in.message.QuizJobPort;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class QuizKafkaAdapter {

    private final ObjectMapper objectMapper;
    private final QuizJobPort quizJobPort; // ✅ 포트 구현체 주입

    public QuizKafkaAdapter(ObjectMapper objectMapper, QuizJobPort quizJobPort) {
        this.objectMapper = objectMapper;
        this.quizJobPort = quizJobPort;
    }

    @KafkaListener(topics = "quiz-topic", groupId = "quiz-group")
    public void receiveMessage(String rawMessage) {
        try {
            // ✅ 메시지 → DTO 역직렬화
            QuizResponseDto dto = objectMapper.readValue(rawMessage, QuizResponseDto.class);
            // ✅ DTO → 도메인 객체 변환
            Quiz quiz = new Quiz(dto.getCrawlingId(), dto.getContent(), null, null);
            
            System.out.println("📥 수신된 요약: {}"+ quiz.getCrawlingId() + " " + quiz.getContent());
            
            // ✅ 포트 인터페이스 호출 (비즈니스 로직 실행)
            quizJobPort.requestQuizJob(quiz); // ✅ 인터페이스 메서드 호출
            
        } catch (Exception e) {
            log.error("메시지 처리 실패: {}", e.getMessage());
        }
    }
}
