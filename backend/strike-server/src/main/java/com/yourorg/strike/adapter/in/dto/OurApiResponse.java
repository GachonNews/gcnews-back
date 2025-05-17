// OurApiResponse.java
package com.yourorg.strike.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
@Schema(description = "API 공통 응답")
public class OurApiResponse<T> {
    @Schema(description = "상태", example = "success, fail")
    private String status;
    @Schema(description = "데이터")
    private T data;
    @Schema(description = "응답 메시지", nullable = true)
    private String message;
}
