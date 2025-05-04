-- 001-create-database.sql
CREATE DATABASE IF NOT EXISTS article_platform;
USE article_platform;

CREATE TABLE `Article` (
    `article_id` INT NOT NULL AUTO_INCREMENT,
    `crawling_id` INT NOT NULL,
    `title` TEXT NOT NULL,
    `category`  TEXT NOT NULL,
    `upload_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `content` TEXT NOT NULL,
    `views` INT NOT NULL DEFAULT 0,
    `article_link` LONGBLOB,
    `img_link` LONGBLOB,
    PRIMARY KEY (`article_id`),
    INDEX idx_article_category (`category`),
    INDEX idx_article_upload (`upload_at`),
);

-- ENUM('POLITICS', 'TECH', 'SPORTS')