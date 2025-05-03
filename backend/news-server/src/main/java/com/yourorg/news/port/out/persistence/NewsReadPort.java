package com.yourorg.news.port.out.persistence;
import com.yourorg.news.domain.entity.News;
import java.util.Optional;

public interface NewsReadPort {
    Optional<News> NewsRequest(Long newsId); // "load" → "find"로 변경
}