package com.yourorg.crawling.adapter.out;

import com.yourorg.crawling.domain.News;
import org.junit.jupiter.api.Test;

class MySQLCrawlingAdapterTest {
    @Test
    void saveNews_printsTitle() {
        MySQLCrawlingAdapter adapter = new MySQLCrawlingAdapter();
        adapter.saveNews(new News("테스트", "내용"));
        // 실제 DB 테스트는 TestContainer 등 활용 가능
    }
}
