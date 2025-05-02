package com.yourorg.crawling.port.out;

import com.yourorg.crawling.domain.News;

public interface QuizRequestPort {
    void requestQuiz(News news);
}
