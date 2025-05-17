package com.yourorg.article.adapter.in.web;

import com.yourorg.article.adapter.in.web.dto.response.OurApiResponse;
import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.port.in.web.ArticleLikeApiPort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/article/like")
@RequiredArgsConstructor
public class ArticleLikeApiAdapter {

    private final ArticleLikeApiPort articleLikeApiPort;

    @Operation(summary = "좋아요 추가")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "좋아요 추가 성공",
                    value = """
                    {
                      "status": "success",
                      "data": null,
                      "message": "좋아요 추가 완료"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409", description = "중복 좋아요",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "좋아요 중복 실패",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "이미 좋아요가 있습니다."
                    }
                    """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<OurApiResponse<Void>> addLike(
        @RequestParam Long userId,
        @RequestParam Long crawlingId
    ) {
        boolean added = articleLikeApiPort.addLike(userId, crawlingId);
        if (!added) {
            return ResponseEntity.status(409)
                .header("Like-Status", "duplicated")
                .body(new OurApiResponse<>("fail", null, "이미 좋아요가 있습니다."));
        }
        return ResponseEntity.ok()
            .header("Like-Status", "added")
            .body(new OurApiResponse<>("success", null, "좋아요 추가 완료"));
    }

    @Operation(summary = "좋아요 취소")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "좋아요 취소 성공",
                    value = """
                    {
                      "status": "success",
                      "data": null,
                      "message": "좋아요 취소 완료"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", description = "좋아요 기록 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "좋아요 기록 없음 실패",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "좋아요 기록이 없습니다."
                    }
                    """
                )
            )
        )
    })
    @DeleteMapping
    public ResponseEntity<OurApiResponse<Void>> removeLike(
        @RequestParam Long userId,
        @RequestParam Long crawlingId
    ) {
        boolean removed = articleLikeApiPort.removeLike(userId, crawlingId);
        if (!removed) {
            return ResponseEntity.status(404)
                .header("Like-Status", "not_found")
                .body(new OurApiResponse<>("fail", null, "좋아요 기록이 없습니다."));
        }
        return ResponseEntity.ok()
            .header("Like-Status", "removed")
            .body(new OurApiResponse<>("success", null, "좋아요 취소 완료"));
    }

    @Operation(summary = "사용자가 누른 좋아요 기사 목록 조회")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "좋아요 기사 목록 성공",
                    value = """
                    {
                      "status": "success",
                      "data": [
                        {
                          "crawlingId": 145,
                          "title": "개발자가 좋아하는 뉴스",
                          "content": "AI가 개발자를 돕는다.",
                          "category": "tech"
                        },
                        {
                          "crawlingId": 147,
                          "title": "클라우드 시장 동향",
                          "content": "전세계 클라우드 도입 가속화",
                          "category": "business"
                        }
                      ],
                      "message": null
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<OurApiResponse<List<ArticleResponse>>> getUserLikes(@PathVariable Long userId) {
        List<ArticleResponse> articles = articleLikeApiPort.getUserLikes(userId);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(new OurApiResponse<>("success", articles, null));
    }
}
