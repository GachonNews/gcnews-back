package com.yourorg.article.domain.service.web;

import com.yourorg.article.port.in.web.ArticleViewPort;
import com.yourorg.article.port.out.persistence.UserWritePort;
import com.yourorg.article.port.out.persistence.ArticleWritePort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yourorg.article.adapter.in.web.dto.response.ArticleViewResult;


@Service
@RequiredArgsConstructor
public class ArticleViewApiService implements ArticleViewPort {

    private final UserWritePort userWritePort;
    private final ArticleWritePort articleWritePort;
    private final ArticleFindPort articleFindPort;

    @Override
    @Transactional
    public ArticleViewResult addView(Long userId, Long crawlingId) {
        // 1. 기사 존재 여부 확인
        if (!articleFindPort.existsByCrawlingId(crawlingId)) {
            return ArticleViewResult.NOT_FOUND;
        }

        // 2. 중복 조회 확인
        if (userWritePort.existsByUserIdAndCrawlingId(userId, crawlingId)) {
            return ArticleViewResult.ALREADY_VIEWED;
        }

        // 3. 조회 기록 저장
        userWritePort.save(new User(userId, crawlingId));

        // 4. 조회수 증가
        articleWritePort.incrementViewCount(crawlingId);

        return ArticleViewResult.SUCCESS;
    }
}
