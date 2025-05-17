package com.yourorg.summary.adapter.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API GET /api/summaries/{crawlingId} 응답용 DTO
 */
@Data
@NoArgsConstructor      // Jackson 직렬화/역직렬화 지원
@AllArgsConstructor     // 모든 필드를 파라미터로 받는 생성자
public class SummaryResponseDto {
    private Long crawlingId;      // 뉴스 크롤링 ID
    private String summaryContent; // 생성된 요약 내용
}