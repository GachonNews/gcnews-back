package com.yourorg.quiz.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * GET /api/quizzes/{quizId} 응답용 DTO
 */
@Getter
@AllArgsConstructor
public class QuizResponseDto {
    private Long crawlingId;
    private String quizContent;
    private Boolean quizAnswer;
}
