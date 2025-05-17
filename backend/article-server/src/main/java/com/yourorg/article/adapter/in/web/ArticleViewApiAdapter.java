package com.yourorg.article.adapter.in.web;

import com.yourorg.article.adapter.in.web.dto.response.OurApiResponse;
import com.yourorg.article.port.in.web.ArticleViewPort;
import com.yourorg.article.adapter.in.web.dto.response.ArticleViewResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article/view")
@RequiredArgsConstructor
public class ArticleViewApiAdapter {

    private final ArticleViewPort articleViewPort;

    @Operation(summary = "기사 조회수 증가")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "조회 성공",
                    value = """
                    {
                      "status": "success",
                      "data": null,
                      "message": "조회수 증가 완료"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "기사 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "기사 없음",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "기사를 찾을 수 없습니다."
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "중복 조회",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "중복 조회",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "이미 조회한 기사입니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<OurApiResponse<Void>> addView(@RequestParam Long userId, @RequestParam Long crawlingId) {
        ArticleViewResult result = articleViewPort.addView(userId, crawlingId);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok(
                    new OurApiResponse<>("success", null, "조회수 증가 완료")
                );
            case ALREADY_VIEWED:
                return ResponseEntity.status(409)
                    .body(new OurApiResponse<>("fail", null, "이미 조회한 기사입니다."));
            case NOT_FOUND:
                return ResponseEntity.status(404)
                    .body(new OurApiResponse<>("fail", null, "기사를 찾을 수 없습니다."));
            default:
                return ResponseEntity.status(500)
                    .body(new OurApiResponse<>("error", null, "알 수 없는 서버 에러"));
        }
    }
}
