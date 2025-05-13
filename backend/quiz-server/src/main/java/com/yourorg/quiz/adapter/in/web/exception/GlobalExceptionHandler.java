package com.yourorg.quiz.adapter.in.web.exception;

import com.yourorg.quiz.adapter.in.web.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            String.valueOf(ex.getStatusCode().value()), // 예: "404"
            ex.getReason() // 예: "퀴즈를 찾을 수 없습니다: ..."
        );
        return ResponseEntity
            .status(ex.getStatusCode())
            .body(errorResponse);
    }
}