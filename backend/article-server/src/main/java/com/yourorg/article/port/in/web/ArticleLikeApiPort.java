package com.yourorg.article.port.in.web;
import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
import java.util.List;


public interface ArticleLikeApiPort {
    void addLike(Long userId, Long crawlingId); 
    void removeLike(Long userId, Long crawlingId);
    List<ArticleResponse> getUserLikes(Long userId); // ✅ 컨트롤러 역할
}