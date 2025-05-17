package com.yourorg.summary.adapter.out.persistence;

import com.yourorg.summary.adapter.out.repository.SummaryJPARepository;
import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.persistence.SummaryWritePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MySQLSummaryWriteAdapter implements SummaryWritePort {
    
    private final SummaryJPARepository summaryRepository;
    
    @Override
    public void saveSummary(Summary summary) {
        // 1) 필수 필드 null 체크
        if (summary.getContent() == null || summary.getSummaryContent() == null) {
            log.error("저장 실패 - 필수 필드 누락: crawlingId={}, content={}, summaryContent={}",
                summary.getCrawlingId(),
                summary.getContent(),
                summary.getSummaryContent()
            );
            return;
        }

        // 2) DB 저장
        summaryRepository.save(summary);
        log.info("📤요약 정보 저장 완료: crawlingId={}", summary.getCrawlingId());
    }
}