package com.yourorg.crawling.port.out.message;

import com.yourorg.crawling.adapter.out.message.dto.NewsRequestDto;

public interface NewsRequestPort {
    void requestNews(NewsRequestDto dto); // ✅ DTO 사용
}
