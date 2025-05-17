package com.yourorg.quiz.port.out.persistence;
import com.yourorg.quiz.domain.entity.Quiz;
public interface QuizWritePort {
    void saveQuiz(Quiz quiz);
}
