package com.yourorg.quiz.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourorg.quiz.domain.entity.Quiz;

public interface QuizJPARepository extends JpaRepository<Quiz, Long> {
        Optional<Quiz> findByCrawlingId(Long crawlingId);
}