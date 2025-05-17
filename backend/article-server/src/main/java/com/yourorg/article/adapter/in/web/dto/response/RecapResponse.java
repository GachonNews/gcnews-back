package com.yourorg.article.adapter.in.web.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RecapResponse {
    private List<ArticleResponse> top5Liked;
    private List<ArticleResponse> top5NotLiked;
}
