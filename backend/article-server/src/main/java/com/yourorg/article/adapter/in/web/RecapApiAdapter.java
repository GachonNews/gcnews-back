package com.yourorg.article.adapter.in.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.yourorg.article.adapter.in.web.dto.RecapResponse;
import com.yourorg.article.port.in.web.RecapApiPort;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/article/recap")
@RequiredArgsConstructor // Lombok으로 생성자 주입
public class RecapApiAdapter {

    private final RecapApiPort recapApiPort;

    @GetMapping("/{userId}/{yearMonth}")
    public ResponseEntity<RecapResponse> getMonthlyRecap(
            @PathVariable Long userId,
            @PathVariable String yearMonth) { // yearMonth: "2025-05"

        RecapResponse response = recapApiPort.getMonthlyRecap(userId, yearMonth);
        return ResponseEntity.ok(response);
    }
}
