package com.yourorg.crawling.adapter.out.persistence;

import com.yourorg.crawling.domain.entity.Crawling;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CrawlingJpaRepository extends JpaRepository<Crawling, Long> {
    Optional<Crawling> findTopByCategoryOrderByUploadAtDesc(String category);
    Crawling save(Crawling crawling);
}
