package com.yourorg.crawling.domain;

import com.yourorg.crawling.port.in.CrawlingTriggerPort;
import com.yourorg.crawling.port.out.NewsSavePort;
import com.yourorg.crawling.port.out.QuizRequestPort;
import com.yourorg.crawling.port.out.SummaryRequestPort;
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

    private final NewsSavePort newsSavePort;
    private final SummaryRequestPort summaryRequestPort;
    private final QuizRequestPort quizRequestPort;

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

            // 1. 세부 카테고리 URL 수집 (Python의 crawl_category_news)
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

            // 2. 각 세부 카테고리에서 뉴스 추출 (Python의 crawl_hankyung_detail_news)
            for (String subCategoryUrl : subCategoryUrls) {
                System.out.println("[2] 세부 카테고리 진입: " + subCategoryUrl);
                try {
                    Document subCategoryDoc = Jsoup.connect(subCategoryUrl).get();
                    Element newsListUl = subCategoryDoc.selectFirst("ul.news-list");
                    if (newsListUl == null) {
                        System.out.println("뉴스 리스트 없음");
                        continue;
                    }

                    // 3. 각 뉴스 항목 파싱
                    for (Element li : newsListUl.select("li")) {
                        // 제목 & 링크
                        Element h2 = li.selectFirst("h2.news-tit");
                        Element a = h2 != null ? h2.selectFirst("a") : null;
                        String title = a != null ? a.text().trim() : "(제목 없음)";
                        String newsLink = a != null ? a.absUrl("href") : null;

                        // 날짜
                        Element dateElem = li.selectFirst("p.txt-date");
                        String dateStr = dateElem != null ? dateElem.text().trim() : "(날짜 없음)";
                        LocalDateTime uploadAt = parseDate(dateStr);

                        // 이미지
                        Element img = li.selectFirst("figure.thumb img");
                        String imgLink = img != null ? img.absUrl("src") : "(이미지 없음)";

                        // 본문 (Python의 crawl_hankyung_content)
                        String content = "";
                        if (newsLink != null && !newsLink.isEmpty()) {
                            try {
                                Document newsDoc = Jsoup.connect(newsLink).get();
                                Element body = newsDoc.selectFirst("div.article-body");
                                if (body != null) {
                                    body.select("br").remove(); // <br> 태그 제거
                                    content = body.text().trim().replaceAll("\n+", "\n");
                                } else {
                                    content = newsDoc.body().text().trim();
                                }
                            } catch (Exception e) {
                                content = "(본문 추출 실패)";
                            }
                        }

                        // News 객체 생성 및 저장
                        News news = new News();
                        news.setTitle(title);
                        news.setCategory(subCategoryUrl);
                        news.setContent(content);
                        news.setUploadAt(uploadAt);
                        news.setNewsLink(newsLink);
                        news.setImgLink(imgLink);

                        newsSavePort.saveNews(news);
                        summaryRequestPort.requestSummary(news);
                        quizRequestPort.requestQuiz(news);

                        // 출력
                        System.out.println("\n".repeat(40));
                        System.out.println("제목: " + title);
                        System.out.println("링크: " + newsLink);
                        System.out.println("이미지: " + imgLink);
                        System.out.println("날짜: " + uploadAt);
                        System.out.println("내용: " + content);

                        Thread.sleep(1000); // 1초 대기
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
