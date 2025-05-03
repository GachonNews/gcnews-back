package com.yourorg.summary.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SUMMARY")  // 테이블명을 "SUMMARY"로 변경 (기존 "NEWS"와 충돌 방지)
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id")  // 컬럼명 명시적 지정
    private Long id;  // int → Long으로 변경 (JPA 권장)

    @Column(name = "news_id", unique = true, nullable = false)
    private Long newsId;  // 뉴스 ID (외부 시스템과 연동)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "summary_content", columnDefinition = "TEXT")
    private String summaryContent;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 생성자
    public Summary(Long newsId, String Content, String summaryContent) {
        this.newsId = newsId;
        this.content = Content;
        this.summaryContent = summaryContent;
    }

    // 요약 업데이트
    public void updateSummary(String summaryContent) {
        this.summaryContent = summaryContent;
    }
}
