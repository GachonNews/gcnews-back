package com.yourorg.summary.adapter.out.persistence;

import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.persistence.SummaryReadPort;
import com.yourorg.summary.adapter.out.repository.SummaryJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MySQLSummaryReadAdapter implements SummaryReadPort {
    private final SummaryJPARepository summaryRepository;

    @Override
    public Optional<Summary> findByCrawlingId(Long crawlingId) {
        System.out.println("📥 전달 요청: " + crawlingId);
        return summaryRepository.findByCrawlingId(crawlingId);
    }
}
