package com.yourorg.strike.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;
import com.yourorg.strike.port.in.web.UserActivityPort;
import com.yourorg.strike.port.out.persistence.StrikeWritePort;
import com.yourorg.strike.port.out.persistence.StrikeReadPort;
import com.yourorg.strike.domain.entity.Strike;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserActivityService implements UserActivityPort {

    private final StrikeWritePort writePort;
    private final StrikeReadPort readPort;

    @Override
    public DeliveryStrikeDto upsertUser(Long userId, DeliveryStrikeDto dto) {
        LocalDate visitDate = dto.getVisitDate();

        // 1) 이번 달 전체 기록을 조회
        List<Strike> monthlyStrikes = readPort.findMonthlyStrikes(userId, visitDate);

        // 2) (newsId, visitDate) 조합이 일치하는 기존 레코드를 스트림으로 찾기
        Optional<Strike> existing = monthlyStrikes.stream()
            .filter(s ->
                s.getNewsId().equals(dto.getNewsId()) &&
                s.getVisitDate().equals(visitDate)
            )
            .findFirst();

        // 3) 존재하면 업데이트, 없으면 새로 생성하여 저장
        Strike saved = existing
            .map(strike -> {
                strike.setSummarized(dto.isSummarized());
                strike.setQuized(dto.isQuized());
                // 필요시 추가 필드 갱신…
                return writePort.saveStrike(strike);
            })
            .orElseGet(() -> {
                Strike newStrike = new Strike(
                    userId,
                    visitDate,
                    dto.getNewsId(),
                    dto.isSummarized(),
                    dto.isQuized()
                );
                return writePort.saveStrike(newStrike);
            });

        // 4) 저장된 엔티티를 다시 DTO로 변환하여 반환
        return DeliveryStrikeDto.fromEntity(saved);
    }
    
}
