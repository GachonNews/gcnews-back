package com.yourorg.crawling.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.yourorg.crawling.port.in.spring_batch.CrawlingTriggerPort;

@RestController
@RequestMapping("/api/crawling")
@RequiredArgsConstructor
public class RestApiAdapter {

    private final CrawlingTriggerPort crawlingTriggerPort;

    @PostMapping("/run")
    public String runCrawling() {
        crawlingTriggerPort.triggerCrawling();
        return "크롤링이 시작되었습니다!";
    }
}
