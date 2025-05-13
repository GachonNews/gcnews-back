package com.yourorg.strike.port.out.persistence;

import com.yourorg.strike.domain.entity.Strike;

import java.time.LocalDate;
import java.util.List;

public interface StrikeReadPort {
    List<Strike> findMonthlyStrikes(Long userId, LocalDate visiDate); //userId의 월별 스트라이크 조회
}
