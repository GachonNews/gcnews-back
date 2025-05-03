package com.yourorg.news.domain.service;

import com.yourorg.news.domain.entity.News;
import com.yourorg.news.port.out.persistence.NewsReadPort;
import com.yourorg.news.port.out.web.NewsDeliveryPort;
import com.yourorg.news.port.in.web.NewsApiPort;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsApiService implements NewsApiPort {

    private final NewsDeliveryPort newsDeliveryPort;  // ✅ 아웃포트 주입
    private final NewsReadPort newsReadPort;  // ✅ 아웃포트 주입

    @Override
    public void NewsRequest(Long newsId) {
        System.out.println("📥 뉴스 요청: " + newsId);

        // 1. 뉴스 ID로 요약 정보 조회
        Optional<news> newsOptional = newsReadPort.NewsRequest(newsId);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            // 2. 요약 정보 전달
            newsDeliveryPort.deliverNews(news);
        } else {
            // 3. 요약 정보가 없는 경우 처리
            System.out.println("No news found for news ID: " + newsId);
        }
    }
}
