package com.yourorg.strike.domain.service;

import com.yourorg.strike.port.in.web.StrikeRequestPort;
import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;
import com.yourorg.strike.port.out.persistence.StrikeReadPort;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StrikeService implements StrikeRequestPort {

    private final StrikeReadPort readPort;

    @Override
    public List<DeliveryStrikeDto> getMonthlyStrikes(Long userId, int year, int month) {
        LocalDate visitDate = LocalDate.of(year, month, 1);
        return readPort.findMonthlyStrikes(userId, visitDate).stream()
                .map(s -> new DeliveryStrikeDto(s.getUserId(),
                s.getNewsId(),
                s.getVisitDate(),
                s.isSummarized(),
                s.isQuized()))
                .toList();
    }
    @Override
    public List<DeliveryStrikeDto> getFriendMonthlyStrikes(Long friendId, int year, int month) {
        LocalDate visitDate = LocalDate.of(year, month, 1);

        return readPort.findMonthlyStrikes(friendId, visitDate).stream()
                .map(s -> new DeliveryStrikeDto(s.getUserId(),
                s.getNewsId(),
                s.getVisitDate(),
                s.isSummarized(),
                s.isQuized()))
                .toList();
    }
}