package com.yourorg.crawling.domain.service;

import com.yourorg.crawling.domain.entity.Crawling;
import com.yourorg.crawling.port.in.spring_batch.CrawlingTriggerPort;
import com.yourorg.crawling.port.out.persistence.CrawlingRepositoryPort;
import com.yourorg.crawling.port.out.message.QuizRequestPort;
import com.yourorg.crawling.port.out.message.SummaryRequestPort;
import com.yourorg.crawling.port.out.message.ArticleRequestPort;
import com.yourorg.crawling.adapter.out.message.dto.QuizRequestDto;
import com.yourorg.crawling.adapter.out.message.dto.SummaryRequestDto;
import com.yourorg.crawling.adapter.out.message.dto.ArticleRequestDto;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService implements CrawlingTriggerPort {

    private final CrawlingRepositoryPort crawlingRepositoryPort;
    private final SummaryRequestPort summaryRequestPort;
    private final QuizRequestPort quizRequestPort;
    private final ArticleRequestPort articleRequestPort;

    private static final String[] MAIN_CATEGORIES = {
        "economy", "financial-market", "industry", "distribution", "it", "international"
    };

    @Override
    public void triggerCrawling() {
        for (String mainCategory : MAIN_CATEGORIES) {
            System.out.println("==== [" + mainCategory + "] 카테고리 크롤링 시작 ====");
            LocalDateTime lastUploadAt = crawlingRepositoryPort.findLatestUploadAtByCategory(mainCategory);
            System.out.println("DB 최신 업로드 시각: " + lastUploadAt);

            List<String> subCategoryUrls = getSubCategoryUrls(mainCategory);

            for (String subCategoryUrl : subCategoryUrls) {
                int page = 1;
                boolean keepCrawling = true;

                while (keepCrawling) {
                    String pageUrl = subCategoryUrl + "?page=" + page;
                    System.out.println("페이지 진입: " + pageUrl);

                    try {
                        Document doc = Jsoup.connect(pageUrl).get();
                        Element articleListUl = doc.selectFirst("ul.news-list");
                        if (articleListUl == null) {
                            System.out.println("뉴스 리스트 없음, 다음 서브카테고리로");
                            break;
                        }

                        Elements liList = articleListUl.select("li");
                        if (liList.isEmpty()) {
                            System.out.println("뉴스 항목 없음, 다음 서브카테고리로");
                            break;
                        }

                        for (Element li : liList) {
                            // 1. 제목 및 링크
                            Element h2 = li.selectFirst("h2.news-tit");
                            Element a = h2 != null ? h2.selectFirst("a") : null;
                            String title = a != null ? a.text().trim() : "(제목 없음)";
                            String articleLink = a != null ? a.absUrl("href") : null;

                            // 2. 날짜
                            Element dateElem = li.selectFirst("p.txt-date");
                            String dateStr = dateElem != null ? dateElem.text().trim() : null;
                            LocalDateTime uploadAt = parseDate(dateStr);

                            // 3. 이미지
                            Element img = li.selectFirst("figure.thumb img");
                            String imgLink = img != null ? img.absUrl("src") : null;

                            // 4. 본문
                            String content = "";
                            if (articleLink != null && !articleLink.isEmpty()) {
                                try {
                                    Document articleDoc = Jsoup.connect(articleLink).get();
                                    Element body = articleDoc.selectFirst("div.article-body");
                                    if (body != null) {
                                        body.select("br").remove();
                                        content = body.text().trim().replaceAll("\n+", "\n");
                                    } else {
                                        content = articleDoc.body().text().trim();
                                    }
                                } catch (Exception e) {
                                    content = "(본문 추출 실패)";
                                }
                            }

                            // 5. DB 최신 업로드 시각과 비교
                            if (lastUploadAt != null && uploadAt != null && !uploadAt.isAfter(lastUploadAt)) {
                                System.out.println("이미 저장된 최신 뉴스보다 오래됨, 이 서브카테고리 크롤링 종료");
                                keepCrawling = false;
                                break;
                            }

                            // 6. 저장 및 메시지 발행
                            Crawling crawling = new Crawling();
                            crawling.setTitle(title);
                            crawling.setCategory(mainCategory);
                            crawling.setContent(content);
                            crawling.setUploadAt(uploadAt);
                            crawling.setArticleLink(articleLink);
                            crawling.setImgLink(imgLink);

                            crawlingRepositoryPort.save(crawling);

                            // 메시지 전송
                            summaryRequestPort.requestSummary(new SummaryRequestDto(
                                crawling.getCrawlingId(), crawling.getContent()));

                            quizRequestPort.requestQuiz(new QuizRequestDto(
                                crawling.getCrawlingId(), crawling.getContent()));

                            articleRequestPort.requestArticle(new ArticleRequestDto(
                                crawling.getCrawlingId(), crawling.getTitle(), crawling.getCategory(),
                                crawling.getContent(), crawling.getArticleLink(), crawling.getImgLink(),
                                crawling.getUploadAt() != null ? crawling.getUploadAt().toString() : ""
                            ));

                            // 로그 출력
                            System.out.println("뉴스 저장: " + title + " | " + uploadAt + " | " + articleLink);

                            Thread.sleep(5000); // 5초 대기 (API 차단 방지)
                        }
                        page++;
                    } catch (Exception e) {
                        System.err.println("페이지 크롤링 실패: " + e.getMessage());
                        break;
                    }
                }
            }
        }
        System.out.println("크롤링 완료!");
    }

    // 서브카테고리 URL 수집
    private List<String> getSubCategoryUrls(String mainCategory) {
        List<String> subCategoryUrls = new ArrayList<>();
        String categoryUrl = "https://www.hankyung.com/" + mainCategory;
        try {
            Document categoryDoc = Jsoup.connect(categoryUrl).get();
            Element gnbUl = categoryDoc.selectFirst("ul.section__gnb");
            if (gnbUl != null) {
                for (Element a : gnbUl.select("a.nav-link")) {
                    String href = a.absUrl("href");
                    if (!href.isEmpty()) {
                        subCategoryUrls.add(href);
                        System.out.println("서브카테고리 링크: " + href);
                    }
                }
            } else {
                // 만약 section__gnb가 없다면 메인 카테고리 자체만 크롤링
                subCategoryUrls.add(categoryUrl);
            }
        } catch (Exception e) {
            System.err.println("서브카테고리 수집 실패: " + e.getMessage());
            // 최소한 메인 카테고리라도 크롤링
            subCategoryUrls.add(categoryUrl);
        }
        return subCategoryUrls;
    }

    // 날짜 파싱
    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null) return null;
        try {
            // 예시: "2024.05.18 13:20"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            return LocalDateTime.parse(dateStr, formatter);
        } catch (Exception e) {
            System.err.println("날짜 파싱 실패: " + dateStr);
            return null;
        }
    }
}
