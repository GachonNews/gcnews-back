package com.yourorg.crawling.port.out.message;

import com.yourorg.crawling.adapter.out.message.dto.QuizRequestDto;

public interface QuizRequestPort {
    void requestQuiz(QuizRequestDto dto); // ✅ DTO 사용
}
