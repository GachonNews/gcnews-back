package com.yourorg.quiz.domain.service;

import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.gemini.GeminiApiPort;
import com.yourorg.quiz.port.out.persistence.QuizSavePort;
import com.yourorg.quiz.port.in.message.QuizJobPort;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizJobService implements QuizJobPort {
    
    private final GeminiApiPort geminiApiPort;
    private final QuizSavePort quizSavePort;

    @Override
    public void requestQuizJob(Quiz quiz) { // ✅ 인터페이스 메서드 정확히 재정의
        String originalContent = quiz.getContent(); // ✅ Quiz 객체 분해
        
        // 1. 외부 API를 통해 요약 생성
        Quiz newQuiz = geminiApiPort.getQuizFromGpt(originalContent);
        
        quiz.setQuizContent(newQuiz.getQuizContent());
        quiz.setQuizAnswer(newQuiz.getQuizAnswer());

        // 2. 도메인 규칙 적용
        // ✅ 새로운 Quiz 객체 생성 (원본/요약 동시에 사용)
        System.out.println(quiz.getQuizContent()+"\n" + quiz.getQuizAnswer() + " 퀴즈 제작 완료 \n");
        
        // 3. 저장
        quizSavePort.saveQuiz(quiz); // ✅ void 반환값 처리
    }
}
