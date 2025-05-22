package com.yourorg.article.port.in.web;

import java.util.List;
import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;

public interface RecapApiPort {
    List<ArticleResponse> getMonthlyRecap(Long userId, String yearMonth);
}
