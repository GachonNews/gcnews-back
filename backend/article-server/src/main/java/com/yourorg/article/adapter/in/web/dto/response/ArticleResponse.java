package com.yourorg.article.adapter.in.web.dto.response;

import lombok.Value;
import com.yourorg.article.domain.entity.Article;

@Value
public class ArticleResponse {
    Long crawlingId;
    String title;
    String category;
    String content;
    String articleLink;
    String imgLink;
    String uploadAt;

    // ✅ **정적 팩토리 메서드 필수**
    public static ArticleResponse fromEntity(Article article) {
        return new ArticleResponse(
            article.getCrawlingId(),
            article.getTitle(),
            article.getCategory(),
            article.getContent(),
            article.getArticleLink(),
            article.getImgLink(),
            article.getUploadAt()
        );
    }
}
