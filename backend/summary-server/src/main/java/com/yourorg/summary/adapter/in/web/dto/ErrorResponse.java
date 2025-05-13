// src/main/java/com/yourorg/article/adapter/in/web/dto/ErrorResponse.java
package com.yourorg.summary.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "에러 응답")
public class ErrorResponse {
    @Schema(description = "에러 코드", example = "에러코드~")
    private String errorCode;

    @Schema(description = "에러 메시지", example = "에러 메세지~~")
    private String message;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
