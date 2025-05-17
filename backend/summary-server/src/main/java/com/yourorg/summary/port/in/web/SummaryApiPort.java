package com.yourorg.summary.port.in.web;

import java.util.Optional;

import com.yourorg.summary.adapter.in.dto.response.SummaryResponseDto;

public interface SummaryApiPort {
    /**
     * DB에서 crawlingId로 요약을 조회해 Optional<SummaryResponseDto>로 반환합니다.
     */
    Optional<SummaryResponseDto> getSummaryByCrawlingId(Long crawlingId);
}
