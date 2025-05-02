package com.yourorg.crawling.adapter.out;

import com.yourorg.crawling.domain.News;
import org.junit.jupiter.api.Test;

class QuizMessageQueueAdapterTest {

    @Test
    void requestQuiz_정상호출() {
        QuizMessageQueueAdapter adapter = new QuizMessageQueueAdapter();
        News news = new News("퀴즈 제목", "퀴즈 본문");

        adapter.requestQuiz(news);

        // 마찬가지로, print/spy/assert 호출 여부만 검증!
    }
}
