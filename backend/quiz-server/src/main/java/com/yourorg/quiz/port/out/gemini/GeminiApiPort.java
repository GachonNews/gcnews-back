package com.yourorg.quiz.port.out.gemini;

import com.yourorg.quiz.domain.entity.Quiz;

public interface GeminiApiPort {
    Quiz getQuizFromGpt(String content);
}
