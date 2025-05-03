package com.yourorg.news.port.out.persistence;
import com.yourorg.news.domain.entity.News;
public interface NewsSavePort {
    void saveNews(News news);
}
