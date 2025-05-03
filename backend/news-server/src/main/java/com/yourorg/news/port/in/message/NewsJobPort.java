package com.yourorg.news.port.in.message;

import com.yourorg.news.domain.entity.News;

public interface NewsJobPort {
    void requestNewsJob(News news);
}
