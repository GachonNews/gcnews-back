// test/domain/service/SummaryServiceTest.java
package com.yourorg.summary.domain.service;

import com.yourorg.summary.domain.entity.Summary;
import com.yourorg.summary.port.out.openai.GptApiPort;
import com.yourorg.summary.port.out.persistence.SummaryReadPort;
import com.yourorg.summary.port.out.persistence.SummarySavePort;
import com.yourorg.summary.port.out.web.SummaryDeliveryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SummaryServiceTest {

    @Mock private GptApiPort gptApiPort;
    @Mock private SummarySavePort summarySavePort;
    @Mock private SummaryReadPort summaryReadPort;
    @Mock private SummaryDeliveryPort summaryDeliveryPort;

    @InjectMocks private SummaryService summaryService;

    @Test
    void 요약_생성_및_저장_성공() {
        // Given
        Summary inputSummary = new Summary(1L, "긴 뉴스 본문...");
        String expectedSummary = "요약 결과";
        
        when(gptApiPort.getSummaryFromGpt(any())).thenReturn(expectedSummary);

        // When
        summaryService.requestSummaryJob(inputSummary);

        // Then
        verify(gptApiPort).getSummaryFromGpt("긴 뉴스 본문...");
        verify(summarySavePort).saveSummary(argThat(summary -> 
            summary.getSummaryContent().equals(expectedSummary)
        ));
    }

    @Test
    void 요약_조회_및_전송_확인() {
        // Given
        Long articleId = 1L;
        Summary expectedSummary = new Summary(articleId, "요약 결과");
        when(summaryReadPort.findByArticleId(articleId)).thenReturn(Optional.of(expectedSummary));

        // When
        Optional<Summary> result = summaryService.findSummaryByArticleId(articleId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(expectedSummary, result.get());
        //verify(summaryDeliveryPort).deliverSummary(expectedSummary);
    }
}
