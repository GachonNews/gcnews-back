package com.yourorg.summary.adapter.in.web;

import com.yourorg.summary.port.in.web.SummaryApiPort;
import com.yourorg.summary.adapter.in.dto.response.SummaryResponseDto;
import com.yourorg.summary.adapter.in.dto.response.OurApiResponse;
import com.yourorg.summary.util.JwtUtil; // JwtUtil import 필요

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryApiAdapter {

    private final SummaryApiPort summaryApiPort;

    @Value("${jwt.secret}")
    private String secretKey;

    @Operation(summary = "요약 단건 조회",
    security = @SecurityRequirement(name = "bearerAuth") )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "성공 예시",
                    value = """
                        {
                            "status": "success",
                            "data": {
                              "crawlingId": 1,
                              "summaryContent": "KIEP, 올해 세계 경제 성장률 전망치를 2.7%로 하향 조정했다.\\n이는 IMF 전망치(2.8%)보다 낮은 수치이며, 미·중 무역 분쟁 심화 및 미국 자국 우선주의 강화 등이 주요 원인으로 분석된다.\\n미국과 중국의 90일 관세 인하 합의는 비정상적인 관세율의 정상화 과정으로 평가되며, 원·달러 환율은 하반기 안정화될 것으로 전망했다.\\n\\n핵심 한 문장: 미·중 무역분쟁 심화와 미국 자국우선주의로 인해 KIEP는 세계 경제 성장률 전망치를 하향 조정하고, 원달러 환율의 하반기 안정화를 예측했다.\\n"
                            },
                            "message": null
                          }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", description = "입력값 오류",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "입력값 오류",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "입력값 오류입니다."
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "요약을 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "실패 예시",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "요약을 찾을 수 없습니다: 888"
                    }
                    """
                )
            )
        )
    })
    @GetMapping(
      value = "/{crawlingId}",
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OurApiResponse<SummaryResponseDto>> getSummary(
            @RequestHeader("Authorization") String token,
            @PathVariable Long crawlingId) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);

        return summaryApiPort
            .getSummaryByCrawlingId(crawlingId)
            .map(dto ->
                ResponseEntity.ok(
                    new OurApiResponse<>("success", dto, null)
                )
            )
            .orElseGet(() ->
                ResponseEntity.status(404)
                    .body(new OurApiResponse<>("fail", null, "요약을 찾을 수 없습니다: " + crawlingId))
            );
    }
}
