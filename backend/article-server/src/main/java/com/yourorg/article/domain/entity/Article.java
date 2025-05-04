package com.yourorg.article.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;  // int → Long으로 변경

    @Column(name = "crawling_id")
    private Long crawlingId;

    private String title;                // 제목

    private String category;             // 카테고리(예: 경제, IT 등)

    @Column(name = "upload_at")
    private String uploadAt;      // 뉴스 날짜/작성일시

    @Column(columnDefinition = "TEXT")
    private String content;              // 본문

    private Integer views;               // 조회수(필요시)

    @Column(name = "article_link")
    private String articleLink;             // 뉴스 상세 URL

    @Column(name = "img_link")
    private String imgLink;              // 썸네일 이미지 URL

    // 기본 생성자 (필드 타입 수정)
    public Article(Long crawlingId, String title, String category, String content,
                String articleLink, String imgLink, String uploadAt) {
        this.crawlingId = crawlingId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.articleLink = articleLink;
        this.imgLink = imgLink;
        this.uploadAt = uploadAt;
    }
}
