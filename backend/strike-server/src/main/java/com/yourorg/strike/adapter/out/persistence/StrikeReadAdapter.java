package com.yourorg.strike.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import com.yourorg.strike.port.out.persistence.StrikeReadPort;
import com.yourorg.strike.adapter.out.repository.StrikeRepository;
import com.yourorg.strike.domain.entity.Strike;

@Component
@RequiredArgsConstructor
public class StrikeReadAdapter implements StrikeReadPort {
     private final StrikeRepository strikeRepository;

   @Override
    public List<Strike> findMonthlyStrikes(Long userId, LocalDate visiDate) {
        int year = visiDate.getYear();
        int month = visiDate.getMonthValue();
         List<Strike> strikes = strikeRepository.findByUserIdAndVisitYearAndMonth(userId, year, month);
         return strikes;
    }
}
