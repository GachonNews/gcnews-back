package com.yourorg.crawling.domain;

import com.yourorg.crawling.port.out.NewsSavePort;
import com.yourorg.crawling.port.out.SummaryRequestPort;
import com.yourorg.crawling.port.out.QuizRequestPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class CrawlingServiceTest {
    @Mock NewsSavePort newsSavePort;
    @Mock SummaryRequestPort summaryRequestPort;
    @Mock QuizRequestPort quizRequestPort;

    @InjectMocks CrawlingService crawlingService;

    @Test
    void triggerCrawling_callsAllPorts() {
        crawlingService.triggerCrawling();
        verify(newsSavePort).saveNews(any(News.class));
        verify(summaryRequestPort).requestSummary(any(News.class));
        verify(quizRequestPort).requestQuiz(any(News.class));
    }
}
