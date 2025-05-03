package com.yourorg.crawling.port.out.message;

import com.yourorg.crawling.adapter.out.message.dto.SummaryRequestDto;

public interface SummaryRequestPort {
    void requestSummary(SummaryRequestDto dto); // ✅ DTO 사용
}
