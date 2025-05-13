package com.yourorg.article.domain.service.exceptionhandler;

public class LikeException {

    // 중복 좋아요 예외
    public static class DuplicateLikeException extends RuntimeException {
        public DuplicateLikeException(String message) {
            super(message);
        }
    }

    // 좋아요 기록 없음 예외
    public static class LikeNotFoundException extends RuntimeException {
        public LikeNotFoundException(String message) {
            super(message);
        }
    }
}
