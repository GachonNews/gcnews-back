package com.yourorg.summary.domain.service;

import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.persistence.SummaryReadPort;
import com.yourorg.summary.port.out.web.SummaryDeliveryPort;
import com.yourorg.summary.port.in.web.SummaryApiPort;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummaryApiService implements SummaryApiPort {

    private final SummaryDeliveryPort summaryDeliveryPort;  // ✅ 아웃포트 주입
    private final SummaryReadPort summaryReadPort;  // ✅ 아웃포트 주입

    @Override
    public void SummaryRequest(Long crawlingId) {
        System.out.println("📥 요약 요청: " + crawlingId);

        // 1. 뉴스 ID로 요약 정보 조회
        Optional<Summary> summaryOptional = summaryReadPort.SummaryRequest(crawlingId);
        if (summaryOptional.isPresent()) {
            Summary summary = summaryOptional.get();
            // 2. 요약 정보 전달
            summaryDeliveryPort.deliverSummary(summary);
        } else {
            // 3. 요약 정보가 없는 경우 처리
            System.out.println("No summary found for article ID: " + crawlingId);
        }
    }
}
