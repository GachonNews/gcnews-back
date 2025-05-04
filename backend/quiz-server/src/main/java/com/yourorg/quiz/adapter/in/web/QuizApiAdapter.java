package com.yourorg.quiz.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.yourorg.quiz.port.in.web.QuizApiPort;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizApiAdapter {

    private final QuizApiPort quizApiPort;

    @GetMapping("/{crawlingId}")
    public String getQuiz(@PathVariable Long crawlingId) {
        quizApiPort.QuizRequest(crawlingId);
        return "조회가 시작되었습니다.";
    }
}