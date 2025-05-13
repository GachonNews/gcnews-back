package com.yourorg.crawling.adapter.in.web;

import com.yourorg.crawling.port.in.spring_batch.CrawlingTriggerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crawling")
@RequiredArgsConstructor
public class RestApiAdapter {

    private final CrawlingTriggerPort crawlingTriggerPort;

    @PostMapping(
        path = "/run",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> runCrawling() {
        crawlingTriggerPort.triggerCrawling();
        return ResponseEntity
                .ok()
                .body("크롤링이 시작되었습니다!");
    }
}
