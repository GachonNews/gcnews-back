package com.yourorg.summary.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Summary")  // 테이블명을 "SUMMARY"로 변경 (기존 "NEWS"와 충돌 방지)
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id")  // 컬럼명 명시적 지정
    private Long id;  // int → Long으로 변경 (JPA 권장)

    @Column(name = "crawling_id", unique = true, nullable = false)
    private Long crawlingId;  // 뉴스 ID (외부 시스템과 연동)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "summary_content", columnDefinition = "TEXT")
    private String summaryContent;

    // 생성자
    public Summary(Long crawlingId, String Content, String summaryContent) {
        this.crawlingId = crawlingId;
        this.content = Content;
        this.summaryContent = summaryContent;
    }

    public Summary(Long crawlingId, String summaryContent) {
        this.crawlingId = crawlingId;
        this.summaryContent = summaryContent;
    }
}
