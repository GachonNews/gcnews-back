package com.yourorg.quiz.adapter.in.web;

import com.yourorg.quiz.adapter.in.web.dto.QuizResponseDto;
import com.yourorg.quiz.port.in.web.QuizApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizApiAdapter {

    private final QuizApiPort quizApiPort;

    @GetMapping("/{crawlingId}")
    public ResponseEntity<QuizResponseDto> getQuizByCrawlingId(@PathVariable Long crawlingId) {
        QuizResponseDto dto = quizApiPort
            .getQuizByCrawlingId(crawlingId)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "퀴즈를 찾을 수 없습니다: " + crawlingId
                )
            );

        return ResponseEntity
            .ok()
            .body(dto);
    }
}

