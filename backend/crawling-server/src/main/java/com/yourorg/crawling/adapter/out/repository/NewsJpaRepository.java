package com.yourorg.crawling.adapter.out.repository;

import com.yourorg.crawling.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsJpaRepository extends JpaRepository<News, Long> {
}