package com.yourorg.quiz.port.in.web;

import com.yourorg.quiz.adapter.in.web.dto.QuizResponseDto;
import java.util.Optional;

public interface QuizApiPort {
    Optional<QuizResponseDto> getQuizByCrawlingId(Long crawlingId);
}