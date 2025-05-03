package com.yourorg.crawling.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourorg.crawling.domain.entity.Crawling;

public interface NewsJpaRepository extends JpaRepository<Crawling, Long> {
}