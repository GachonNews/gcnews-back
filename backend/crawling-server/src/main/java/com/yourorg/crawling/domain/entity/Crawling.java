package com.yourorg.crawling.domain.entity;

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
@Table(name = "Crawling")
public class Crawling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crawling_id")
    private Long crawlingId;

    private String title;                // 제목

    private String category;             // 카테고리(예: 경제, IT 등)

    @Column(name = "upload_at")
    private java.time.LocalDateTime uploadAt;  // 뉴스 날짜/작성일시

    @Column(columnDefinition = "TEXT")
    private String content;              // 본문

    @Column(name = "article_link")
    private String articleLink;             // 뉴스 상세 URL

    @Column(name = "img_link")
    private String imgLink;              // 썸네일 이미지 URL
}
