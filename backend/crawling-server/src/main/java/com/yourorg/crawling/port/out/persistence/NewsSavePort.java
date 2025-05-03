package com.yourorg.crawling.port.out.persistence;

import com.yourorg.crawling.domain.entity.Crawling;

public interface NewsSavePort {
    void saveNews(Crawling news);
}