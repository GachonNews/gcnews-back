-- 001-create-database.sql
CREATE DATABASE IF NOT EXISTS quiz_platform;
USE quiz_platform;

CREATE TABLE `Quiz` (
    `quiz_id` BIGINT NOT NULL AUTO_INCREMENT,
    `crawling_id` BIGINT NOT NULL,
    `content` TEXT NOT NULL,
    `quiz_content` TEXT NOT NULL,
    `quiz_answer` BOOLEAN NOT NULL,
    PRIMARY KEY (`quiz_id`),
);

