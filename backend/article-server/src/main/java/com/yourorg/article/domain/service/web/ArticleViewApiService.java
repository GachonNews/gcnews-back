package com.yourorg.article.domain.service.web;

import com.yourorg.article.port.in.web.ArticleViewPort;
import com.yourorg.article.port.out.persistence.UserWritePort;
import com.yourorg.article.port.out.persistence.ArticleWritePort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.domain.entity.User;
import com.yourorg.article.domain.service.exceptionhandler.ViewException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleViewApiService implements ArticleViewPort {

    private final UserWritePort userWritePort;
    private final ArticleWritePort articleWritePort;
    private final ArticleFindPort articleFindPort;

    @Override
    @Transactional
    public void addView(Long userId, Long crawlingId) {
        // 1. 기사 존재 여부 확인
        if (!articleFindPort.existsByCrawlingId(crawlingId)) {
            throw new ViewException.ArticleNotFoundException("기사가 존재하지 않습니다: " + crawlingId);
        }

        // 2. 중복 조회 확인 (이미 본 경우 예외 발생)
        if (userWritePort.existsByUserIdAndCrawlingId(userId, crawlingId)) {
            throw new ViewException.DuplicateViewException("이미 조회한 기사입니다: " + crawlingId);
        }

        // 3. 조회 기록 저장
        userWritePort.save(new User(userId, crawlingId));

        // 4. 조회수 증가
        articleWritePort.incrementViewCount(crawlingId);
    }
}
