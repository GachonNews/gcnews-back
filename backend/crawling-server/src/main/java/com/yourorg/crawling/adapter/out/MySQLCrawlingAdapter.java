package com.yourorg.crawling.adapter.out;


import com.yourorg.crawling.domain.News;
import com.yourorg.crawling.port.out.NewsSavePort;
import org.springframework.stereotype.Repository;
@Repository
public class MySQLCrawlingAdapter implements NewsSavePort {
    // JPA, Spring Data, MyBatis 등 활용
    @Override
    public void saveNews(News news) {
        // 실제로는 JPA, MyBatis, JDBC로 구현
        System.out.println("[DB저장] " + news.getTitle());
    }
}