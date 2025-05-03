package com.yourorg.news.adapter.out.repository;

import com.yourorg.news.domain.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsJPARepository extends JpaRepository<News, Long> {
}