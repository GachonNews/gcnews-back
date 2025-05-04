package com.yourorg.quiz.port.out.persistence;
import java.util.Optional;

import com.yourorg.quiz.domain.entity.Quiz;

public interface QuizReadPort {
    Optional<Quiz> QuizRequest(Long crawlingId); // "load" → "find"로 변경
}