package com.yourorg.crawling.adapter.out;

import com.yourorg.crawling.adapter.out.repository.NewsJpaRepository;
import com.yourorg.crawling.domain.News;
import com.yourorg.crawling.port.out.NewsSavePort;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLCrawlingAdapter implements NewsSavePort {

    private final NewsJpaRepository newsJpaRepository;

    public MySQLCrawlingAdapter(NewsJpaRepository newsJpaRepository) {
        this.newsJpaRepository = newsJpaRepository;
    }

    @Override
    public void saveNews(News news) {
        newsJpaRepository.save(news); // JPA로 실제 DB 저장
    }
}

