package com.yourorg.quiz.port.in.web;

public interface QuizApiPort {
    void QuizRequest(Long crawlingId);  // ✅ 컨트롤러 역할
}
