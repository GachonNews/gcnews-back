// src/main/java/com/yourorg/article/adapter/in/web/GlobalExceptionHandler.java

package com.yourorg.article.adapter.in.exceptionhandler;

import com.yourorg.article.domain.service.web.ArticleViewApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArticleViewApiService.ArticleNotFoundException.class)
    public ResponseEntity<?> handleArticleNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", "error", "message", ex.getMessage()));
    }

    @ExceptionHandler(ArticleViewApiService.DuplicateViewException.class)
    public ResponseEntity<?> handleDuplicateView(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("status", "error", "message", ex.getMessage()));
    }
}
