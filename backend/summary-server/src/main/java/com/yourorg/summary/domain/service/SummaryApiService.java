package com.yourorg.summary.domain.service;

import com.yourorg.summary.adapter.in.web.dto.SummaryResponseDto;
import com.yourorg.summary.port.in.web.SummaryApiPort;
import com.yourorg.summary.port.out.persistence.SummaryReadPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;     // ← 추가
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Web API를 통해 요약 정보를 조회하는 서비스 구현체
 */
@Service("summaryApiService")
@Primary                                       // ← 이 어노테이션을 추가합니다
@RequiredArgsConstructor
@Slf4j
public class SummaryApiService implements SummaryApiPort {

    private final SummaryReadPort readPort;

    /**
     * 주어진 crawlingId에 대한 요약 정보를 조회합니다.
     */
    @Override
    public Optional<SummaryResponseDto> getSummaryByCrawlingId(Long crawlingId) {
        log.info("Service 조회 요청: crawlingId={}", crawlingId);
        return readPort.findByCrawlingId(crawlingId)
            .map(entity -> new SummaryResponseDto(
                entity.getCrawlingId(),
                entity.getSummaryContent()
            ));
    }
}
