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

import org.apache.commons.lang3.tuple.Pair;

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
            LocalDateTime lastUploadAt = crawlingRepositoryPort.findLatestUploadAtByCategory(mainCategory);

            // 1. (수정) 서브카테고리 URL과 이름 한 번에 수집
            List<Pair<String, String>> subCategories = new ArrayList<>(); // name, url
            String categoryUrl = "https://www.hankyung.com/" + mainCategory;
            try {
                Document categoryDoc = Jsoup.connect(categoryUrl).get();
                Element gnbUl = categoryDoc.selectFirst("ul.section__gnb");
                if (gnbUl != null) {
                    for (Element a : gnbUl.select("a.nav-link")) {
                        String href = a.absUrl("href");
                        String name = a.text().trim();
                        if (!href.isEmpty()) {
                            subCategories.add(Pair.of(name, href)); // ← 수정: Pair.of 사용
                            System.out.println("서브카테고리: " + name + " | 링크: " + href);
                        }
                    }
                } else {
                    subCategories.add(Pair.of(mainCategory, categoryUrl)); // ← 수정: Pair.of 사용
                }
            } catch (Exception e) {
                subCategories.add(Pair.of(mainCategory, categoryUrl)); // ← 수정: Pair.of 사용
            }

            // 2. 각 서브카테고리에서 반복
            for (Pair<String, String> subCategory : subCategories) {
                String subCategoryName = subCategory.getKey();
                String subCategoryUrl  = subCategory.getValue();

                // 이하 기존 로직 동일
                int page = 1;
                int lastPage = 1;
                boolean foundOldPage = false;

                while (true) {
                    String pageUrl = subCategoryUrl + "?page=" + page;
                    try {
                        Document doc = Jsoup.connect(pageUrl).get();
                        Element articleListUl = doc.selectFirst("ul.news-list");
                        if (articleListUl == null) break;
                        Elements liList = articleListUl.select("li");
                        if (liList.isEmpty()) break;

                        LocalDateTime oldestOnPage = liList.stream().map(li -> {
                            Element dateElem = li.selectFirst("p.txt-date");
                            String dateStr = dateElem != null ? dateElem.text().trim() : null;
                            try {
                                return dateStr == null ? null : LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
                            } catch (Exception e) { return null; }
                        }).filter(d -> d != null).min(LocalDateTime::compareTo).orElse(null);

                        if (lastUploadAt != null && oldestOnPage != null && !oldestOnPage.isAfter(lastUploadAt)) {
                            lastPage = page;
                            foundOldPage = true;
                            break;
                        }
                        page++;
                    } catch (Exception e) {
                        break;
                    }
                }
                if (!foundOldPage) lastPage = page - 1;
                if (lastPage < 1) lastPage = 1;
                if (lastPage > 3) lastPage = 3;

                for (int p = lastPage; p >= 1; p--) {
                    String pageUrl = subCategoryUrl + "?page=" + p;
                    try {
                        Document doc = Jsoup.connect(pageUrl).get();
                        Element articleListUl = doc.selectFirst("ul.news-list");
                        if (articleListUl == null) break;
                        Elements liList = articleListUl.select("li");
                        if (liList.isEmpty()) break;

                        for (Element li : liList) {
                            // 제목, 링크, 날짜 등등 추출
                            Element h2 = li.selectFirst("h2.news-tit");
                            Element a = h2 != null ? h2.selectFirst("a") : null;
                            String title = a != null ? a.text().trim() : "(제목 없음)";
                            String articleLink = a != null ? a.absUrl("href") : null;

                            Element dateElem = li.selectFirst("p.txt-date");
                            String dateStr = dateElem != null ? dateElem.text().trim() : null;
                            LocalDateTime uploadAt = null;
                            try {
                                uploadAt = dateStr == null ? null : LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
                            } catch (Exception e) { /* 무시 */ }

                            Element img = li.selectFirst("figure.thumb img");
                            String imgLink = img != null ? img.absUrl("src") : null;

                            String content = "";
                            if (articleLink != null && !articleLink.isEmpty()) {
                                try {
                                    Document articleDoc = Jsoup.connect(articleLink).get();
                                    Element body = articleDoc.selectFirst("div.article-body");
                                    if (body != null) {
                                        body.select("br").remove();
                                        content = body.text().trim().replaceAll("\n+", "\n");
                                    }
                                    else content = articleDoc.body().text().trim();
                                } catch (Exception e) {
                                    content = "(본문 추출 실패)";
                                }
                            }

                            if (lastUploadAt != null && uploadAt != null && !uploadAt.isAfter(lastUploadAt)) {
                                continue;
                            }

                            // === 여기! Crawling에 서브카테고리명 저장 ===
                            Crawling crawling = new Crawling();
                            crawling.setTitle(title);
                            crawling.setCategory(mainCategory);
                            crawling.setSubCategory(subCategoryName); // 여기서 저장!
                            crawling.setContent(content);
                            crawling.setUploadAt(uploadAt);
                            crawling.setArticleLink(articleLink);
                            crawling.setImgLink(imgLink);

                            crawlingRepositoryPort.save(crawling);

                            summaryRequestPort.requestSummary(new SummaryRequestDto(
                                    crawling.getCrawlingId(), crawling.getContent()));
                            quizRequestPort.requestQuiz(new QuizRequestDto(
                                    crawling.getCrawlingId(), crawling.getContent()));
                            articleRequestPort.requestArticle(new ArticleRequestDto(
                                    crawling.getCrawlingId(), crawling.getTitle(),
                                    crawling.getCategory(), // 여기도 필요시 전달!
                                    crawling.getSubCategory(),
                                    crawling.getContent(), crawling.getArticleLink(), crawling.getImgLink(),
                                    crawling.getUploadAt() != null ? crawling.getUploadAt().toString() : ""
                            ));

                            System.out.println("뉴스 저장: " + title + " | " + uploadAt + " | " + articleLink);

                            Thread.sleep(60 * 1000);
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }
        System.out.println("크롤링 완료!");
    }    
}
