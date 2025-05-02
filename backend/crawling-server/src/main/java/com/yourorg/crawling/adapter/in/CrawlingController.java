package com.yourorg.crawling.adapter.in;

import com.yourorg.crawling.port.in.CrawlingTriggerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crawling")
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingTriggerPort crawlingTriggerPort;

    @PostMapping("/run")
    public String runCrawling() {
        crawlingTriggerPort.triggerCrawling();
        return "크롤링이 시작되었습니다!";
    }
}
