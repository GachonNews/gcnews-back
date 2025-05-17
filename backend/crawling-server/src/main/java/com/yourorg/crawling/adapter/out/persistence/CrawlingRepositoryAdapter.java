package com.yourorg.crawling.adapter.out.persistence;

import com.yourorg.crawling.domain.entity.Crawling;
import com.yourorg.crawling.port.out.persistence.CrawlingRepositoryPort;
import com.yourorg.crawling.adapter.out.persistence.CrawlingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CrawlingRepositoryAdapter implements CrawlingRepositoryPort {

    private final CrawlingJpaRepository jpaRepository;

    @Override
    public java.time.LocalDateTime findLatestUploadAtByCategory(String category) {
        return jpaRepository.findTopByCategoryOrderByUploadAtDesc(category)
                .map(Crawling::getUploadAt)
                .orElse(null);
    }

    @Override
    public Crawling save(Crawling crawling) {
        return jpaRepository.save(crawling);
    }
}
