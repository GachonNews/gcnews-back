-- 001-create-database.sql
CREATE DATABASE IF NOT EXISTS crawling_platform;
USE crawling_platform;

CREATE TABLE `Crawling` (
    `crawling_id` INT NOT NULL AUTO_INCREMENT,
    `title` TEXT NOT NULL,
    `category`  TEXT NOT NULL,
    `upload_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `content` TEXT NOT NULL,
    `article_link` LONGBLOB,
    `img_link` LONGBLOB,
    PRIMARY KEY (`crawling_id`),
    INDEX idx_article_category (`category`),
    INDEX idx_article_upload (`upload_at`),
);

-- ENUM('POLITICS', 'TECH', 'SPORTS')