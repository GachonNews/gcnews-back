package com.yourorg.quiz.port.out.web;
import com.yourorg.quiz.domain.entity.Quiz;

public interface QuizDeliveryPort {
    void deliverQuiz(Quiz quiz);
}
