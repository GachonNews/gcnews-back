package com.yourorg.crawling.port.out.persistence;

import com.yourorg.crawling.domain.entity.Crawling;

public interface ArticleSavePort {
    void saveArticle(Crawling crawling);
}