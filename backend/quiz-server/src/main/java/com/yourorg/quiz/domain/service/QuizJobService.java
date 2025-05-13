package com.yourorg.quiz.domain.service;

import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.gemini.GeminiApiPort;
import com.yourorg.quiz.port.out.persistence.QuizSavePort;
import com.yourorg.quiz.port.in.message.QuizJobPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizJobService implements QuizJobPort {
    
    private final GeminiApiPort geminiApiPort;
    private final QuizSavePort quizSavePort;

    @Override
    public void requestQuizJob(Quiz quiz) {
        // 1) 원본 콘텐츠 추출
        String originalContent = quiz.getContent();  
        log.info("퀴즈 작업 요청: originalContent={}", originalContent);

        // 2) 외부 API로부터 퀴즈 생성
        Quiz generated = geminiApiPort.getQuizFromGpt(originalContent);
        log.info("생성된 퀴즈: content={}, answer={}", 
                 generated.getQuizContent(), generated.getQuizAnswer());

        // 3) 엔티티에 결과 반영
        quiz.setQuizContent(generated.getQuizContent());
        quiz.setQuizAnswer(generated.getQuizAnswer());

        // 4) 저장
        quizSavePort.saveQuiz(quiz);
        log.info("퀴즈 저장 완료: crawlingId={}", quiz.getCrawlingId());
    }
}
