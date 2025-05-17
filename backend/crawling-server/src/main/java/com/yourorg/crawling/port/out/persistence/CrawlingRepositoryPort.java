package com.yourorg.crawling.port.out.persistence;

import com.yourorg.crawling.domain.entity.Crawling;

public interface CrawlingRepositoryPort {
    java.time.LocalDateTime findLatestUploadAtByCategory(String category);
    Crawling save(Crawling crawling);
}
