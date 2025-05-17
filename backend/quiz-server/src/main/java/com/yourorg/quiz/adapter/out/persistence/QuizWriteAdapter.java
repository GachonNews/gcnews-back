package com.yourorg.quiz.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.yourorg.quiz.adapter.out.repository.QuizJPARepository;
import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.persistence.QuizWritePort;

@Component
public class QuizWriteAdapter implements QuizWritePort {
    
    private final QuizJPARepository quizRepository;
    
    public QuizWriteAdapter(QuizJPARepository quizRepository) {
        this.quizRepository = quizRepository;
    }
    
    @Override
    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        System.out.println("📤 퀴즈 정보 저장 완료");
    }
}