package com.yourorg.strike.adapter.in.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.yourorg.strike.adapter.in.dto.OurApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        MethodArgumentTypeMismatchException.class,
        MissingServletRequestParameterException.class,
        HttpMessageNotReadableException.class,
        MethodArgumentNotValidException.class // @Valid 검증 실패까지 포함하려면
    })
    public ResponseEntity<?> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest()
            .body(new OurApiResponse<>("fail", null, "입력값 오류입니다."));
    }

    // 필요시 더 세분화된 예외도 커버 가능
}
