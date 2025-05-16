package com.yourorg.strike.adapter.out.web;

import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;
import com.yourorg.strike.domain.entity.Strike;
import com.yourorg.strike.port.out.web.StrikeDeliveryPort;


import lombok.RequiredArgsConstructor; // Import constructor generation
import lombok.extern.slf4j.Slf4j; // Import logging
import org.springframework.stereotype.Component;


@Slf4j // Enable logging
@Component // Mark as a Spring component
@RequiredArgsConstructor
public class StrikeDeliveryAdapter implements StrikeDeliveryPort {

    @Override
    public void deliverStrikeInfo(Strike strike) {

        // DTO → Entity 변환
        DeliveryStrikeDto strikeDto = new DeliveryStrikeDto(
            strike.getUserId(),
            strike.getNewsId(),
            strike.getVisitDate(),
            strike.isSummarized(),
            strike.isQuized()
        );

        // 로깅 최적화
        log.info(
            """
            =================================================
            ✅ 요약 정보 전송 (게이트웨이 연결 대기 중)
            📌 유저 ID: {}
            📌 친구 ID: {}
            📌 스트라이크 날짜: {}
            📌 스트라이크 타입: {}
            📌 스트라이크 카운트: {}
            =================================================
            """,
            strikeDto.getUserId(),
            strikeDto.getNewsId(),
            strikeDto.getVisitDate(),
            strikeDto.isSummarized() ? "요약" : "퀴즈",
            strikeDto.isQuized() ? "퀴즈" : "요약"
        );
    }
}
