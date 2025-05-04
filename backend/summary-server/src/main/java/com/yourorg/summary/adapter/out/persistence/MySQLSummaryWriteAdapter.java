package com.yourorg.summary.adapter.out.persistence;

import com.yourorg.summary.adapter.out.repository.SummaryJPARepository;
import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.persistence.SummarySavePort;

import org.springframework.stereotype.Component;

@Component
public class MySQLSummaryWriteAdapter implements SummarySavePort {
    
    private final SummaryJPARepository summaryRepository;
    
    public MySQLSummaryWriteAdapter(SummaryJPARepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }
    
    @Override
    public void saveSummary(Summary summary) {
        summaryRepository.save(summary);
        System.out.println("📤 요약 정보 저장 완료");
    }
}