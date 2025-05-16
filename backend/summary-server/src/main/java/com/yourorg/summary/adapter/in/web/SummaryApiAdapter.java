package com.yourorg.summary.adapter.in.web;

import com.yourorg.summary.adapter.in.web.dto.SummaryResponseDto;
import com.yourorg.summary.port.in.web.SummaryApiPort;
import com.yourorg.summary.adapter.in.web.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryApiAdapter {

    private final SummaryApiPort summaryApiPort;

    @Operation(summary = "요약 단건 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "404",
            description = "요약을 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @GetMapping(
      value = "/{crawlingId}",
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SummaryResponseDto> getSummary(@PathVariable Long crawlingId) {
        // Optional에서 꺼내고, 없으면 404
        SummaryResponseDto dto = summaryApiPort
            .getSummaryByCrawlingId(crawlingId)
            .orElseThrow(() -> 
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "요약을 찾을 수 없습니다: " + crawlingId
                )
            );

        return ResponseEntity
            .ok()
            .body(dto);
    }
}
