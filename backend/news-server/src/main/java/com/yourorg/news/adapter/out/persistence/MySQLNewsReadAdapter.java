package com.yourorg.news.adapter.out.persistence;

import com.yourorg.news.domain.entity.News;
import com.yourorg.news.port.out.persistence.NewsReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.yourorg.news.adapter.out.repository.NewsJPARepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MySQLNewsReadAdapter implements NewsReadPort {

    private final NewsJPARepository newsRepository;

    @Override
    public Optional<News> NewsRequest(Long newsId) { // ✅ Optional 반환
        System.out.println("📥 전달 요청: " + newsId);
        return newsRepository.findByNewsId(newsId); // ✅ findByNewsId 메서드 사용
    }
}
