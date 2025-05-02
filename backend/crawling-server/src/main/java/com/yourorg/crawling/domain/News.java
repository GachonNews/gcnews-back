package com.yourorg.crawling.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NEWS")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private int newsId;

    private String title;                // 제목

    private String category;             // 카테고리(예: 경제, IT 등)

    @Column(name = "writer_id")
    private Integer writerId;            // (옵션) 기자 사번 등

    @Column(name = "upload_at")
    private java.time.LocalDateTime uploadAt;  // 뉴스 날짜/작성일시

    @Column(columnDefinition = "TEXT")
    private String content;              // 본문

    private Integer views;               // 조회수(필요시)

    @Column(name = "summary_content")
    private String summaryContent;       // 요약 (필요시)

    @Column(name = "news_link")
    private String newsLink;             // 뉴스 상세 URL

    @Column(name = "img_link")
    private String imgLink;              // 썸네일 이미지 URL

    // 필요하다면 추가 생성자
    public News(String title, String category, String content,
                String newsLink, String imgLink, java.time.LocalDateTime uploadAt) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.newsLink = newsLink;
        this.imgLink = imgLink;
        this.uploadAt = uploadAt;
    }
}
