package com.yourorg.quiz.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
@Schema(description = "API 통일 응답")
public class OurApiResponse<T> {
    @Schema(description = "상태", example = "success / fail / error")
    private String status;
    @Schema(description = "실제 데이터 객체")
    private T data;
    @Schema(description = "에러 메시지/추가 메시지", nullable = true)
    private String message;
}
