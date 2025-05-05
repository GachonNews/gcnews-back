package com.yourorg.article.port.in.web;

import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
import java.util.List;

public interface ArticleQueryApiPort {
    List<ArticleResponse> articleAllRequest();
    List<ArticleResponse> articleCategoryRequest(String category);
}
