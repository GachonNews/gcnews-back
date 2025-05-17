// ApiResponse.java
package com.yourorg.article.adapter.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "API 응답 포맷")
public class OurApiResponse<T> {
    @Schema(description = "status", example = "success / fail / error")
    private String status;

    @Schema(description = "data")
    private T data;

    @Schema(description = "message", nullable = true)
    private String message;
}
