package com.yourorg.summary.domain.service;

import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.gemini.GeminiApiPort;
import com.yourorg.summary.port.out.persistence.SummarySavePort;
import com.yourorg.summary.port.in.message.SummaryJobPort;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SummaryJobService implements SummaryJobPort {
    
    private final GeminiApiPort geminiApiPort;
    private final SummarySavePort summarySavePort;

    @Override
    public void requestSummaryJob(Summary summary) { // ✅ 인터페이스 메서드 정확히 재정의
        String originalContent = summary.getContent(); // ✅ Summary 객체 분해
        
        // 1. 외부 API를 통해 요약 생성
        String summaryContent = geminiApiPort.getSummaryFromGpt(originalContent);
        
        summary.setSummaryContent(summaryContent);

        // 2. 도메인 규칙 적용
        // ✅ 새로운 Summary 객체 생성 (원본/요약 동시에 사용)
        System.out.println(summary.getSummaryContent() + " 요약 제작 완료 \n");

        // 3. 저장
        summarySavePort.saveSummary(summary); // ✅ void 반환값 처리
    }
}
