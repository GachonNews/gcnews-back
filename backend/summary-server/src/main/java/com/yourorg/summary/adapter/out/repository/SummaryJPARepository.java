package com.yourorg.summary.adapter.out.repository;

import com.yourorg.summary.domain.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummaryJPARepository extends JpaRepository<Summary, Long> {
    Optional<Summary> findByCrawlingId(Long crawlingId);
}