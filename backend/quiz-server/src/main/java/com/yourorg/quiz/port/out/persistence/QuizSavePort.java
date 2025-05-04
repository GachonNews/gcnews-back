package com.yourorg.quiz.port.out.persistence;
import com.yourorg.quiz.domain.entity.Quiz;
public interface QuizSavePort {
    void saveQuiz(Quiz quiz);
}
