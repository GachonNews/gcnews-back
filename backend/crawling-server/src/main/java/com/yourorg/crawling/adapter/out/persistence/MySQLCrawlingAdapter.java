package com.yourorg.crawling.adapter.out.persistence;

import com.yourorg.crawling.adapter.out.repository.CrawlingJpaRepository;
import com.yourorg.crawling.domain.entity.Crawling;
import com.yourorg.crawling.port.out.persistence.ArticleSavePort;

import org.springframework.stereotype.Repository;

@Repository
public class MySQLCrawlingAdapter implements ArticleSavePort {

    private final CrawlingJpaRepository crwalingJpaRepository;

    public MySQLCrawlingAdapter(CrawlingJpaRepository crwalingJpaRepository) {
        this.crwalingJpaRepository = crwalingJpaRepository;
    }

    @Override
    public void saveArticle(Crawling crawling) {
        crwalingJpaRepository.save(crawling); // JPA로 실제 DB 저장
    }
}
