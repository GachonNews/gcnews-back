package com.yourorg.news.port.out.web;
import com.yourorg.news.domain.entity.News;

public interface NewsDeliveryPort {
    void deliverNews(News news);
}
