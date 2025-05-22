package com.yourorg.article.domain.service.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.port.in.web.RecapApiPort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecapService implements RecapApiPort {

    private final ArticleFindPort articleFindPort;

    @Override
    public List<ArticleResponse> getMonthlyRecap(Long userId, String yearMonth) {
        return articleFindPort.findTop5LikedByUser(userId, yearMonth)
                .stream()
                .map(ArticleResponse::fromEntity)
                .toList();
    }
}
