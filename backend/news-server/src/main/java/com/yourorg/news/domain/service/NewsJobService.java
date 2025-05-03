package com.yourorg.news.domain.service;

import com.yourorg.news.domain.entity.News;
import com.yourorg.news.port.out.persistence.NewsSavePort;
import com.yourorg.news.port.in.message.NewsJobPort;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsJobService implements NewsJobPort {
    
    private final NewsSavePort newsSavePort;

    @Override
    public void requestNewsJob(News news) { // ✅ 인터페이스 메서드 정확히 재정의
        newsSavePort.saveNews(newNews); // ✅ void 반환값 처리
    }
}
