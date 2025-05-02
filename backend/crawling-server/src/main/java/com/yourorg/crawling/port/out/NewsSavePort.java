package com.yourorg.crawling.port.out;

import com.yourorg.crawling.domain.News;

public interface NewsSavePort {
    void saveNews(News news);
}