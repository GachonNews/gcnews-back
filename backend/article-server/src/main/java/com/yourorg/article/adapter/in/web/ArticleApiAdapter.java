package com.yourorg.article.adapter.in.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.adapter.in.web.dto.response.OurApiResponse;
import com.yourorg.article.port.in.web.ArticleQueryApiPort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleApiAdapter {

    private final ArticleQueryApiPort articleQueryApiPort;

    @Operation(summary = "카테고리별 기사 조회")
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
                            "title": "KIEP, 세계경제 성장률 2.7% 전망…美中 관세유예는 \"정상화되는 과정\"",
                            "category": "economy",
                            "content": "게티이미지뱅크 대외경제정책연구원(KIEP)이 올해 세계 경제 성장률 전망치를 2.7%로 내려 잡았다. 미국과 중국이 서로 경쟁적으로 올리던 관세를 90일간 낮추기로 최근 합의한 데 대해서는 “(미국이) 강하게 질렀던 숫자가 대부분 정상화되는 과정”이라고 평가했다.KIEP는 13일 ‘2025년 세계 경제 전망’을 발표하며 다음과 같이 설명했다. KIEP는 올해 세계 경제 성장률을 지난해 11월만 해도 3.0%로 전망했지만 이날 0.3%p 하향조정한 2.7%를 제시했다. 이는 국제통화기금(IMF)이 지난 4월 내놓은 전망치(2.8%)보다도 낮은 수치다.KIEP는 “미국 신행정부 출범과 함께 자국우선주의, 보호무역주의가 심화됐다”며 “금융시장 변동성이 확대되고, 실질 부채 부담도 증가하면서 성장격차가 커지고 있다”고 말했다. 이어 “관세 불확실성, 인플레이션 재발, 재정적자 증가 등의 영향으로 미국 국채금리 상승 요인이 상존한다”며 “반면 경기 침체에 따른 미국 연방준비제도의 금리 인하, 미국 국채에 대한 선호 증가 등이 나타나면 장기금리가 하향할 가능성도 있다”고 설명했다.원·달러 환율에 대해서는 약달러로의 전환세를 전망했다. KIEP는 “연초 이후 관세정책 발표 등으로 달러화 약세가 지속되는 중”이라며 “하반기 미국경기 둔화 및 기준금리 인하 가능성, 환율정책 등이 약달러를 전망하게 하는 요인”이라고 말했다. 이어 “원·달러 환율도 상반기 높은 변동성을 보이다가, 하반기 미국 금리 인하 및 관세 협상 진전으로 점진적인 안정화가 기대된다”고 덧붙였다.주요국 전망으로는 미국이 불확실한 정책 방향으로 인한 경제 심리 악화로 올해 1.3% 성장할 것으로 점쳐졌다. 중국은 미·중 갈등과 대규모 경기부양책이 서로 상쇄되며 4.1% 성장률을 기록할 것으로 전망됐다.한편 이날 이시욱 KIEP 원장은 도널드 트럼프 미국 행정부발(發) 관세 전쟁에 대해 “무역 질서 격변으로 세계 경제가 표류 중”이라면서도 “비정상적이었던 숫자들이 대부분 정상화되는 과정을 거칠 것”이라고 분석했다.미국과 중국이 서로 부과한 상호관세를 90일간 대폭 낮추기로 한 데 대해 이 원장은 “만약 미국이 원래 중국에 부과하기로 했던 145% 관세가 유지된다면 미국의 유효관세율은 33.5% 수준”이라며 “이는 1872년(38%) 이후 가장 높은 수치이기 때문에 사실상 불가능한 숫자라고 보는 게 맞다”고 말했다. 이어 “트럼프 행정부 관세정책의 목표는 단순히 무역적자를 줄인다는 것 외 (관세를) 감세에 대한 대체자원으로 생각하고 있는 것”이라며 “10% 정도의 기본관세를 유지하며 연방재정을 높이고 나머지 관세부분은 협상의 가능성이 있다고 본다”고 설명했다.미국 달러가 약세인 이유에 대해서는 “과연 달러가 안정자산인지에 대한 의문이 생기면서 예측했던 것과는 다른 방향으로 흘러간 것”이라며 “트럼프 행정부가 들어선 뒤 미국 달러가 상당부분 강세를 보이다가 갑자기 약세로 전환됐다”고 설명했다. 그러면서도 “구조적으로 안전자산으로서의 달러에 대한 신뢰가 무너졌다고 보기는 어렵다”고 덧붙였다.남정민 기자 peux@hankyung.com",
                            "articleLink": "https://www.hankyung.com/article/202505139360i",
                            "imgLink": "https://img.hankyung.com/photo/202505/01.40461532.3.jpg",
                            "uploadAt": "2025-05-13T14:05"
                            }
                        ],
                        "message": null
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
            description = "유효하지 않은 카테고리",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OurApiResponse.class),
                examples = @ExampleObject(
                    name = "유효성 실패 예시",
                    summary = "유효하지 않은 카테고리",
                    value = """
                    {
                      "status": "fail",
                      "data": null,
                      "message": "유효하지 않은 카테고리입니다."
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{category}")
    public ResponseEntity<OurApiResponse<List<ArticleResponse>>> getCategoryArticle(@PathVariable String category) {
        List<ArticleResponse> articles = articleQueryApiPort.articleCategoryRequest(category);

        if (articles == null || articles.isEmpty()) {
            return ResponseEntity.status(404)
                .body(new OurApiResponse<>("fail", null, "해당 카테고리의 기사가 없습니다."));
        }

        return ResponseEntity.ok(
            new OurApiResponse<>("success", articles, null)
        );
    }
}
