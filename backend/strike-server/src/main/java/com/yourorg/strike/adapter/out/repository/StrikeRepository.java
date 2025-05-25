package com.yourorg.strike.adapter.out.repository;

import com.yourorg.strike.domain.entity.Strike;
import com.yourorg.strike.domain.entity.StrikeMapping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StrikeRepository extends JpaRepository<Strike, StrikeMapping> {
    @Query("SELECT s FROM Strike s " +
           "WHERE s.id.userId = :userId " +
           "AND YEAR(s.id.visitDate) = :year " +
           "AND MONTH(s.id.visitDate) = :month")
    List<Strike> findByUserIdAndVisitYearAndMonth(
        @Param("userId") Long userId,
        @Param("year") int year,
        @Param("month") int month
    );

}
