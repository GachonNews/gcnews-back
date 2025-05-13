package com.yourorg.summary.domain.service;

import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.in.message.SummaryJobPort;
import com.yourorg.summary.port.out.gemini.GeminiApiPort;
import com.yourorg.summary.port.out.persistence.SummarySavePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryJobService implements SummaryJobPort {

    private final GeminiApiPort geminiApiPort;
    private final SummarySavePort summarySavePort;

    /**
     * Kafka로부터 전달된 Summary 엔티티를 받아,
     * 외부 API로 요약을 생성한 뒤 DB에 저장합니다.
     *
     * @param summary 크롤링 ID와 원문 콘텐츠가 담긴 Summary 객체
     */
    @Override
    @Transactional
    public void requestSummaryJob(Summary summary) {
        // 1) 원본 콘텐츠 null 또는 빈 문자열인지 확인
        String original = summary.getContent();
        if (original == null || original.isBlank()) {
            log.warn("원본 콘텐츠가 없습니다. crawlingId={}", summary.getCrawlingId());
            return;  // 요약할 내용이 없으면 처리를 중단
        }

        // 2) 외부 Gemini API 호출로 요약 생성
        String generated = geminiApiPort.getSummaryFromGpt(original);
        if (generated == null || generated.isBlank()) {
            log.warn("요약 생성 실패, 빈 결과 반환. crawlingId={}", summary.getCrawlingId());
            // 실패 시 기본 안내 메시지 설정
            generated = "요약 실패: 생성된 내용이 없습니다.";
        }

        // 3) 엔티티에 생성된 요약문 세팅
        summary.setSummaryContent(generated);

        // 4) 최종적으로 DB에 저장
        summarySavePort.saveSummary(summary);
        log.info("📤요약 저장 완료: crawlingId={}", summary.getCrawlingId());
    }
}
