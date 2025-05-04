-- 001-create-database.sql
CREATE DATABASE IF NOT EXISTS summary_platform;
USE summary_platform;

CREATE TABLE `Summary` (
    `summary_id` INT NOT NULL AUTO_INCREMENT,
    `article_id` INT NOT NULL,
    `content` TEXT NOT NULL,
    `quiz_content` TEXT NOT NULL,
    `quiz_answer` BOOLEAN NOT NULL,
    PRIMARY KEY (`summary_id`),
);

