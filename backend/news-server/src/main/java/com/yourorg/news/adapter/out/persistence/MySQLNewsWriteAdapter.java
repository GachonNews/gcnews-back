package com.yourorg.news.adapter.out.persistence;

import com.yourorg.news.adapter.out.repository.NewsJPARepository;
import com.yourorg.news.domain.entity.News;
import com.yourorg.news.port.out.persistence.NewsSavePort;

import org.springframework.stereotype.Component;

@Component
public class MySQLNewsWriteAdapter implements NewsSavePort {
    
    private final NewsJPARepository newsRepository;
    
    public MySQLNewsWriteAdapter(NewsJPARepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    
    @Override
    public void saveNews(News news) {
        newsRepository.save(news);
    }
}