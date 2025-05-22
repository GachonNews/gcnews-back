package com.yourorg.article.adapter.in.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.adapter.in.web.dto.response.OurApiResponse;
import com.yourorg.article.port.in.web.ArticleQueryApiPort;
import com.yourorg.article.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleApiAdapter {

    private final ArticleQueryApiPort articleQueryApiPort;

    @Value("${jwt.secret}")
    private String secretKey;

    // 허용 카테고리 목록 선언 (필요시 수정)
    private static final Set<String> VALID_CATEGORIES = Set.of(
        "economy", "politics", "sports", "it", "world", "culture"
    );

    @Operation(
        summary = "카테고리별 기사 조회",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "성공 예시",
                    summary = "카테고리 기사 조회 성공",
                    value = """
                    {
                        "status": "success",
                        "data": [
                            {
                            "crawlingId": 1,
                            "title": "KIEP, 세계경제 성장률 2.7% 전망…美中 관세유예는 \\\"정상화되는 과정\\\"",
                            "category": "economy",
                            "content": "...",
                            "articleLink": "https://www.hankyung.com/article/202505139360i",
                            "imgLink": "https://img.hankyung.com/photo/202505/01.40461532.3.jpg",
                            "uploadAt": "2025-05-13T14:05"
                            }
                        ],
                        "message": "전송 완료"
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "카테고리 없음/기사 없음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "실패 예시",
                    summary = "카테고리 또는 기사 없음",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "해당 카테고리의 기사가 없습니다."
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "입력값 오류",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "유효성 실패 예시",
                    summary = "입력값 오류",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "입력값 오류입니다."
                    }
                    """
                )
            )
        )
    })
    @GetMapping("category/{category}")
    public ResponseEntity<OurApiResponse<List<ArticleResponse>>> getCategoryArticle(
            @RequestHeader("Authorization") String token,
            @PathVariable String category) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);

        // 카테고리 값이 허용된 목록에 없으면 400 반환 (입력값 오류)
        if (!VALID_CATEGORIES.contains(category)) {
            return ResponseEntity.badRequest()
                .body(new OurApiResponse<>("fail", null, "입력값 오류입니다."));
        }

        List<ArticleResponse> articles = articleQueryApiPort.articleCategoryRequest(category);

        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.status(404)
                .body(new OurApiResponse<>("fail", null, "해당 카테고리의 기사가 없습니다."));
        }

        return ResponseEntity.ok(
            new OurApiResponse<>("success", articles, null)
        );
    }
    @GetMapping("subcategory/{subcategory}")
    public ResponseEntity<OurApiResponse<List<ArticleResponse>>> getSubCategoryArticle(
            @RequestHeader("Authorization") String token,
            @PathVariable String subcategory) {
        String userId = JwtUtil.getUserIdFromToken(token.replace("Bearer ", ""), secretKey);

        // // 카테고리 값이 허용된 목록에 없으면 400 반환 (입력값 오류)
        // if (!VALID_CATEGORIES.contains(subcategory)) {
        //     return ResponseEntity.badRequest()
        //         .body(new OurApiResponse<>("fail", null, "입력값 오류입니다."));
        // }

        List<ArticleResponse> articles = articleQueryApiPort.articleSubCategoryRequest(subcategory);

        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.status(404)
                .body(new OurApiResponse<>("fail", null, "해당 카테고리의 기사가 없습니다."));
        }

        return ResponseEntity.ok(
            new OurApiResponse<>("success", articles, null)
        );
    }
}
