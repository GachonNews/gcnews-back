package com.yourorg.crawling.adapter.out.persistence;

import com.yourorg.crawling.adapter.out.repository.NewsJpaRepository;
import com.yourorg.crawling.domain.entity.Crawling;
import com.yourorg.crawling.port.out.persistence.NewsSavePort;

import org.springframework.stereotype.Repository;

@Repository
public class MySQLCrawlingAdapter implements NewsSavePort {

    private final NewsJpaRepository newsJpaRepository;

    public MySQLCrawlingAdapter(NewsJpaRepository newsJpaRepository) {
        this.newsJpaRepository = newsJpaRepository;
    }

    @Override
    public void saveNews(Crawling news) {
        newsJpaRepository.save(news); // JPA로 실제 DB 저장
    }
}

