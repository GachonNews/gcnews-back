package com.yourorg.crawling.domain.service;

import com.yourorg.crawling.domain.entity.Crawling;
import com.yourorg.crawling.port.in.spring_batch.CrawlingTriggerPort;
import com.yourorg.crawling.port.out.message.QuizRequestPort;
import com.yourorg.crawling.port.out.message.SummaryRequestPort;
import com.yourorg.crawling.port.out.persistence.ArticleSavePort;
import com.yourorg.crawling.port.out.message.ArticleRequestPort;
import com.yourorg.crawling.adapter.out.message.dto.QuizRequestDto;
import com.yourorg.crawling.adapter.out.message.dto.SummaryRequestDto;
import com.yourorg.crawling.adapter.out.message.dto.ArticleRequestDto;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService implements CrawlingTriggerPort {

    private final ArticleSavePort articleSavePort;
    private final SummaryRequestPort summaryRequestPort;
    private final QuizRequestPort quizRequestPort;
    private final ArticleRequestPort articleRequestPort;

    // 메인 카테고리 URL (Python의 main_category와 동일)
    private static final String[] MAIN_CATEGORIES = {
        "economy",
        "financial-market",
        "industry",
        "distribution",
        "it",
        "international"
    };

    @Override
    public void triggerCrawling() {
        for (String mainCategory : MAIN_CATEGORIES) {
            String categoryUrl = "https://www.hankyung.com/" + mainCategory;
            System.out.println("[1] 메인 카테고리 진입: " + categoryUrl);

            // 1. 세부 카테고리 URL 수집 (Python의 crawl_category_article)
            List<String> subCategoryUrls = new ArrayList<>();
            try {
                Document categoryDoc = Jsoup.connect(categoryUrl).get();
                Element gnbUl = categoryDoc.selectFirst("ul.section__gnb");
                if (gnbUl != null) {
                    for (Element a : gnbUl.select("a.nav-link")) {
                        String href = a.absUrl("href");
                        if (!href.isEmpty()) {
                            subCategoryUrls.add(href);
                            System.out.println(">> 세부 카테고리 링크: " + href);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("세부 카테고리 수집 실패: " + e.getMessage());
                continue;
            }

            // 2. 각 세부 카테고리에서 뉴스 추출 (Python의 crawl_hankyung_detail_article)
            for (String subCategoryUrl : subCategoryUrls) {
                System.out.println("[2] 세부 카테고리 진입: " + subCategoryUrl);
                try {
                    Document subCategoryDoc = Jsoup.connect(subCategoryUrl).get();
                    Element articleListUl = subCategoryDoc.selectFirst("ul.news-list");
                    if (articleListUl == null) {
                        System.out.println("뉴스 리스트 없음");
                        continue;
                    }

                    // 3. 각 뉴스 항목 파싱
                    for (Element li : articleListUl.select("li")) {
                        // 제목 & 링크
                        Element h2 = li.selectFirst("h2.news-tit");
                        Element a = h2 != null ? h2.selectFirst("a") : null;
                        String title = a != null ? a.text().trim() : "(제목 없음)";
                        String articleLink = a != null ? a.absUrl("href") : null;

                        // 날짜
                        Element dateElem = li.selectFirst("p.txt-date");
                        String dateStr = dateElem != null ? dateElem.text().trim() : "(날짜 없음)";
                        LocalDateTime uploadAt = parseDate(dateStr);

                        // 이미지
                        Element img = li.selectFirst("figure.thumb img");
                        String imgLink = img != null ? img.absUrl("src") : "(이미지 없음)";

                        // 본문 (Python의 crawl_hankyung_content)
                        String content = "";
                        if (articleLink != null && !articleLink.isEmpty()) {
                            try {
                                Document articleDoc = Jsoup.connect(articleLink).get();
                                Element body = articleDoc.selectFirst("div.article-body");
                                if (body != null) {
                                    body.select("br").remove(); // <br> 태그 제거
                                    content = body.text().trim().replaceAll("\n+", "\n");
                                } else {
                                    content = articleDoc.body().text().trim();
                                }
                            } catch (Exception e) {
                                content = "(본문 추출 실패)";
                            }
                        }

                        // Article 객체 생성 및 저장
                        Crawling crawling = new Crawling();
                        crawling.setTitle(title);
                        crawling.setCategory(mainCategory);
                        crawling.setContent(content);
                        crawling.setUploadAt(uploadAt);
                        crawling.setArticleLink(articleLink);
                        crawling.setImgLink(imgLink);

                        articleSavePort.saveArticle(crawling);

                        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(
                            (long) crawling.getCrawlingId(),
                            crawling.getContent()
                        );
                        summaryRequestPort.requestSummary(summaryRequestDto); // DTO 전송

                        QuizRequestDto quizRequestDto = new QuizRequestDto(
                            (long) crawling.getCrawlingId(),
                            crawling.getContent()
                        );
                        quizRequestPort.requestQuiz(quizRequestDto);

                        ArticleRequestDto articleRequestDto = new ArticleRequestDto(
                            (long) crawling.getCrawlingId(),
                            crawling.getTitle(),
                            crawling.getCategory(),
                            crawling.getContent(),
                            crawling.getArticleLink(),
                            crawling.getImgLink(),
                            crawling.getUploadAt().toString()
                        );
                        articleRequestPort.requestArticle(articleRequestDto);

                        // 출력
                        System.out.println("\n".repeat(40));
                        System.out.println("뉴스 ID: " + crawling.getCrawlingId());
                        System.out.println("제목: " + title);
                        System.out.println("링크: " + articleLink);
                        System.out.println("이미지: " + imgLink);
                        System.out.println("날짜: " + uploadAt);
                        System.out.println("내용: " + content);

                        Thread.sleep(20000);// 1초 대기
                    }
                } catch (Exception e) {
                    System.err.println("세부 카테고리 처리 실패: " + e.getMessage());
                }
            }
        }
        System.out.println("크롤링 완료!");
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        } catch (Exception e) {
            return null;
        }
    }
}
