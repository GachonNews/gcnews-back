package com.yourorg.crawling.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Crawling", indexes = {
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_upload_at", columnList = "uploadAt")
})
public class Crawling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crawling_id")
    private Long crawlingId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(name = "upload_at", nullable = false)
    private LocalDateTime uploadAt;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(name = "article_link", nullable = false)
    private String articleLink;

    @Column(name = "img_link")
    private String imgLink;  // 썸네일은 nullable 허용
}
