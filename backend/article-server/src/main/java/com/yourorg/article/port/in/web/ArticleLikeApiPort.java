package com.yourorg.article.port.in.web;
import java.util.List;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;


public interface ArticleLikeApiPort {
    boolean addLike(Long userId, Long crawlingId); 
    boolean removeLike(Long userId, Long crawlingId);
    List<ArticleResponse> getUserLikes(Long userId); // ✅ 컨트롤러 역할
}