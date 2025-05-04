package com.yourorg.quiz.domain.service;

import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.persistence.QuizReadPort;
import com.yourorg.quiz.port.out.web.QuizDeliveryPort;
import com.yourorg.quiz.port.in.web.QuizApiPort;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizApiService implements QuizApiPort {

    private final QuizDeliveryPort quizDeliveryPort;  // ✅ 아웃포트 주입
    private final QuizReadPort quizReadPort;  // ✅ 아웃포트 주입

    @Override
    public void QuizRequest(Long crawlingId) {
        System.out.println("📥 요약 요청: " + crawlingId);

        // 1. 뉴스 ID로 요약 정보 조회
        Optional<Quiz> quizOptional = quizReadPort.QuizRequest(crawlingId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();

            // 2. 요약 정보 전달
            quizDeliveryPort.deliverQuiz(quiz);
        } else {
            // 3. 요약 정보가 없는 경우 처리
            System.out.println("No quiz found for crawlingId ID: " + crawlingId);
        }
    }
}
