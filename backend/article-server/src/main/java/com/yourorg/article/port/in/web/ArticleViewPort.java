package com.yourorg.article.port.in.web;
import com.yourorg.article.adapter.in.web.dto.response.ArticleViewResult;

public interface ArticleViewPort {
    ArticleViewResult addView(Long userId, Long crawlingId); 
}
