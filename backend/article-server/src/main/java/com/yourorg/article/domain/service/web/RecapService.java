package com.yourorg.article.domain.service.web;

import com.yourorg.article.port.in.web.RecapApiPort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.adapter.in.web.dto.RecapResponse;
import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
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
    public RecapResponse getMonthlyRecap(Long userId, String yearMonth) {
        return new RecapResponse(
            getTop5LikedByUser(userId, yearMonth),    // 좋아요 Top5
            getTop5NotLikedByUser(userId, yearMonth)  // 좋아요 안 한 Top5
        );
    }

    private List<ArticleResponse> getTop5LikedByUser(Long userId, String yearMonth) {
        return articleFindPort.findTop5LikedByUser(userId, yearMonth)
                .stream()
                .map(ArticleResponse::fromEntity)
                .toList();
    }

    private List<ArticleResponse> getTop5NotLikedByUser(Long userId, String yearMonth) {
        return articleFindPort.findTop5NotLikedByUserAndMonth(userId, yearMonth)
                .stream()
                .map(ArticleResponse::fromEntity)
                .toList();
    }
}
