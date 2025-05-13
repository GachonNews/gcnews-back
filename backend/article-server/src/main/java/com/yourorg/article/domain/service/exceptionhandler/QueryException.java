package com.yourorg.article.domain.service.exceptionhandler;

public class QueryException {

    // 카테고리에 해당하는 기사 없음 예외
    public static class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException(String message) {
            super(message);
        }
    }

    // 유효하지 않은 카테고리 예외
    public static class InvalidCategoryException extends IllegalArgumentException {
        public InvalidCategoryException(String message) {
            super(message);
        }
    }
}
