package com.yourorg.summary.adapter.out.persistence;

import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.persistence.SummaryReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.yourorg.summary.adapter.out.repository.SummaryJPARepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MySQLSummaryReadAdapter implements SummaryReadPort {

    private final SummaryJPARepository summaryRepository;

    @Override
    public Optional<Summary> SummaryRequest(Long crawlingId) { // ✅ Optional 반환
        System.out.println("📥 전달 요청: " + crawlingId);
        return summaryRepository.findByCrawlingId(crawlingId); // ✅ findByArticleId 메서드 사용
    }
}
