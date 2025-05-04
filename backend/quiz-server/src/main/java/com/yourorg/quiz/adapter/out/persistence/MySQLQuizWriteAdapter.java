package com.yourorg.quiz.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.yourorg.quiz.adapter.out.repository.QuizJPARepository;
import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.persistence.QuizSavePort;

@Component
public class MySQLQuizWriteAdapter implements QuizSavePort {
    
    private final QuizJPARepository quizRepository;
    
    public MySQLQuizWriteAdapter(QuizJPARepository quizRepository) {
        this.quizRepository = quizRepository;
    }
    
    @Override
    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        System.out.println("📤 퀴즈 정보 저장 완료");
    }
}