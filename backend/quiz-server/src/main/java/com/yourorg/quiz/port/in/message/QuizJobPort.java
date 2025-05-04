package com.yourorg.quiz.port.in.message;

import com.yourorg.quiz.domain.entity.Quiz;

public interface QuizJobPort {
    void requestQuizJob(Quiz quiz);
}
