
CREATE DATABASE IF NOT EXISTS article_platform;
USE article_platform;

-- 🔒 Article 테이블 (인덱스 내부 포함)
CREATE TABLE `Article` (
    `article_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `crawling_id` BIGINT NOT NULL,
    `title` TEXT NOT NULL,
    `category` ENUM('economy', 'financial-market', 'industry', 'distribution', 'it', 'international') NOT NULL,
    `upload_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `content` TEXT NOT NULL,
    `views` BIGINT NOT NULL DEFAULT 0,
    `article_link` LONGBLOB,
    `img_link` LONGBLOB,
    INDEX idx_crawling_id (crawling_id) -- ✨ 중요: 테이블 생성 시 인덱스 즉시 생성
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_likes` (
    `user_id` BIGINT NOT NULL,
    `crawling_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `crawling_id`),
    FOREIGN KEY (`crawling_id`) REFERENCES `Article`(crawling_id) -- ✅ article_id 대신 crawling_id 참조
);
