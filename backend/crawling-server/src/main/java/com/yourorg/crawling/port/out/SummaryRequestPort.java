package com.yourorg.crawling.port.out;

import com.yourorg.crawling.domain.News;

public interface SummaryRequestPort {
    void requestSummary(News news);
}