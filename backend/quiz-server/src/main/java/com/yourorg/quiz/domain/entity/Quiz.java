package com.yourorg.quiz.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Quiz")  // 테이블명을 "SUMMARY"로 변경 (기존 "NEWS"와 충돌 방지)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")  // 컬럼명 명시적 지정
    private Long id;  // int → Long으로 변경 (JPA 권장)

    @Column(name = "crawling_id", unique = true, nullable = false)
    private Long crawlingId;  // 뉴스 ID (외부 시스템과 연동)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "quiz_content", columnDefinition = "TEXT")
    private String quizContent;

    @Column(name = "quiz_answer", columnDefinition = "Boolean")
    private Boolean quizAnswer;

    // 생성자
    public Quiz(Long crawlingId, String Content, String quizContent, Boolean quizAnswer) {
        this.crawlingId = crawlingId;
        this.content = Content;
        this.quizContent = quizContent;
        this.quizAnswer = false;
    }

    public Quiz(Long crawlingId, String quizContent, Boolean quizAnswer) {
        this.crawlingId = crawlingId;
        this.quizContent = quizContent;
        this.quizAnswer = false;
    }
}
