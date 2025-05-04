package com.yourorg.summary.adapter.in.web;

import com.yourorg.summary.port.in.web.SummaryApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class SummaryApiAdapter {

    private final SummaryApiPort summaryApiPort;

    @GetMapping("/{crawlingId}")
    public String getSummary(@PathVariable Long crawlingId) {
        summaryApiPort.SummaryRequest(crawlingId);
        return "조회가 시작되었습니다.";
    }
}