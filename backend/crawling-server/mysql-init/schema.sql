-- User(회원)
CREATE TABLE `User` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50),
    `gender` BOOLEAN,
    `age` INT,
    `join_at` DATETIME,
    `user_img` BLOB,
    PRIMARY KEY (`user_id`)
);

-- Friend(친구)
CREATE TABLE `Friend` (
    `user_id` INT NOT NULL,
    `friend_id` INT NOT NULL,
    PRIMARY KEY (`user_id`, `friend_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`friend_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- NEWS(뉴스)
CREATE TABLE `NEWS` (
    `news_id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100),
    `category` VARCHAR(50),
    `writer_id` INT,
    `upload_at` DATETIME,
    `content` VARCHAR(2000),
    `views` INT DEFAULT 0,
    `summary_content` VARCHAR(1000),
    `news_img` LONGBLOB,
    PRIMARY KEY (`news_id`)
);

-- like_read(읽음/좋아요 기록)
CREATE TABLE `like_read` (
    `user_id` INT NOT NULL,
    `news_id` INT NOT NULL,
    -- 예시: 실제 읽음/좋아요 정보도 넣을 수 있음
    `is_read` BOOLEAN DEFAULT FALSE,
    `is_like` BOOLEAN DEFAULT FALSE,
    `read_at` DATETIME,
    `like_at` DATETIME,
    PRIMARY KEY (`user_id`, `news_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`news_id`) REFERENCES `NEWS`(`news_id`) ON DELETE CASCADE
);

-- Quiz(퀴즈)
CREATE TABLE `Quiz` (
    `quiz_id` INT NOT NULL AUTO_INCREMENT,
    `news_id` INT NOT NULL,
    `quiz_content` VARCHAR(1000),
    `answer` BOOLEAN NOT NULL,  -- 또는 TINYINT(1), 0/1만 허용시 CHECK (answer IN (0,1))
    PRIMARY KEY (`quiz_id`),
    FOREIGN KEY (`news_id`) REFERENCES `NEWS`(`news_id`) ON DELETE CASCADE
);

-- VisitCalendar(방문 캘린더)
CREATE TABLE `VisitCalendar` (
    `visit_at` DATETIME NOT NULL,
    `user_id` INT NOT NULL,
    `news_id` INT NOT NULL,
    `is_summarized` BOOLEAN,
    `is_quized` BOOLEAN,
    PRIMARY KEY (`visit_at`, `user_id`, `news_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`news_id`) REFERENCES `NEWS`(`news_id`) ON DELETE CASCADE
);
