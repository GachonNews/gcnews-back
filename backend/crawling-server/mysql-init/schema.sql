-- 001-create-database.sql
CREATE DATABASE IF NOT EXISTS news_platform;
USE news_platform;

-- 002-create-tables.sql
CREATE TABLE `User` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `gender` ENUM('M', 'F') NOT NULL CHECK (`gender` IN ('M', 'F')),
    `age` INT NOT NULL CHECK (`age` BETWEEN 0 AND 100),
    `join_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_img` VARCHAR(255),
    PRIMARY KEY (`user_id`),
    UNIQUE (`name`)
);

CREATE TABLE `Friend` (
    `user_id` INT NOT NULL,
    `friend_id` INT NOT NULL,
    PRIMARY KEY (`user_id`, `friend_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`friend_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    CHECK (`user_id` <> `friend_id`)
);

CREATE TABLE `News` (
    `news_id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `category` ENUM('POLITICS', 'TECH', 'SPORTS') NOT NULL,
    `writer_id` INT NOT NULL,
    `upload_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `content` TEXT NOT NULL,
    `views` INT NOT NULL DEFAULT 0,
    `summary_content` TEXT,
    `news_img` LONGBLOB,
    PRIMARY KEY (`news_id`),
    INDEX idx_news_category (`category`),
    INDEX idx_news_upload (`upload_at`),
    FOREIGN KEY (`writer_id`) REFERENCES `User`(`user_id`) ON DELETE SET NULL
);

CREATE TABLE `User_Like` (
    `user_id` INT NOT NULL,
    `news_id` INT NOT NULL,
    `like_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `news_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`),
    FOREIGN KEY (`news_id`) REFERENCES `News`(`news_id`)
);

CREATE TABLE `User_Read` (
    `user_id` INT NOT NULL,
    `news_id` INT NOT NULL,
    `read_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `news_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`),
    FOREIGN KEY (`news_id`) REFERENCES `News`(`news_id`)
);

CREATE TABLE `Quiz` (
    `quiz_id` INT NOT NULL AUTO_INCREMENT,
    `news_id` INT NOT NULL,
    `quiz_content` TEXT NOT NULL,
    `answer` TINYINT(1) NOT NULL CHECK (`answer` IN (0, 1)),
    `question_type` ENUM('TRUE_FALSE', 'MULTIPLE_CHOICE') DEFAULT 'TRUE_FALSE',
    PRIMARY KEY (`quiz_id`),
    INDEX idx_quiz_news (`news_id`),
    FOREIGN KEY (`news_id`) REFERENCES `News`(`news_id`) ON DELETE CASCADE
);

CREATE TABLE `Visit_Calendar` (
    `visit_at` DATETIME NOT NULL,
    `user_id` INT NOT NULL,
    `news_id` INT NOT NULL,
    `is_summarized` BOOLEAN NOT NULL DEFAULT FALSE,
    `is_quized` BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`visit_at`, `user_id`, `news_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`),
    FOREIGN KEY (`news_id`) REFERENCES `News`(`news_id`)
);
