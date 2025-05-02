package com.yourorg.crawling.adapter.out;

import com.yourorg.crawling.domain.News;
import org.junit.jupiter.api.Test;

class SummaryMessageQueueAdapterTest {

    @Test
    void requestSummary_정상호출() {
        SummaryMessageQueueAdapter adapter = new SummaryMessageQueueAdapter();
        News news = new News("테스트 제목", "테스트 본문");
        
        adapter.requestSummary(news);

        // 실제 메시지큐 연동 없이, System.out/로깅/spy로 동작만 확인
        // 외부 MQ 연동 테스트는 Stub/Moq/Embedded 환경에서 별도 가능
    }
}
