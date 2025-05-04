package com.yourorg.crawling.port.out.message;

import com.yourorg.crawling.adapter.out.message.dto.ArticleRequestDto;

public interface ArticleRequestPort {
    void requestArticle(ArticleRequestDto dto); // ✅ DTO 사용
}
