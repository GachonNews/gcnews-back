package com.yourorg.news.adapter.in.web;

import com.yourorg.news.port.in.web.NewsApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class NewsApiAdapter {

    private final NewsApiPort newsApiPort;

    @GetMapping("/{newsId}")
    public String getNews(@PathVariable Long newsId) {
        newsApiPort.NewsRequest(newsId);
        return "조회가 시작되었습니다.";
    }
}