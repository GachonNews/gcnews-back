package com.yourorg.news.port.in.web;

public interface NewsApiPort {
    void NewsRequest(Long newsId);  // ✅ 컨트롤러 역할
}
