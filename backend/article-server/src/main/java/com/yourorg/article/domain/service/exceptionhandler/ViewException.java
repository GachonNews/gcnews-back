package com.yourorg.article.domain.service.exceptionhandler;

public class ViewException {

    // 기사 없음 예외
    public static class ArticleNotFoundException extends RuntimeException {
        public ArticleNotFoundException(String message) {
            super(message);
        }
    }

    // 중복 조회 예외
    public static class DuplicateViewException extends RuntimeException {
        public DuplicateViewException(String message) {
            super(message);
        }
    }
}
