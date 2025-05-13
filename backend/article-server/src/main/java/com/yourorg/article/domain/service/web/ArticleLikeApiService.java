package com.yourorg.article.domain.service.web;

import com.yourorg.article.domain.entity.User;
import com.yourorg.article.domain.entity.UserMapping;
import com.yourorg.article.port.in.web.ArticleLikeApiPort;
import com.yourorg.article.port.out.persistence.UserWritePort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;
import com.yourorg.article.adapter.in.web.dto.ArticleResponse;
import com.yourorg.article.domain.service.exceptionhandler.LikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleLikeApiService implements ArticleLikeApiPort {

    private final UserWritePort userLikePort;
    private final ArticleFindPort articlePort;

    @Override
    @Transactional
    public void addLike(Long userId, Long crawlingId) {
        if(userLikePort.existsByUserIdAndCrawlingId(userId, crawlingId)) {
            throw new LikeException.DuplicateLikeException("이미 좋아요를 누른 기사입니다: " + crawlingId);
        }
        
        User userLike = new User();
        userLike.setUserId(userId);
        userLike.setCrawlingId(crawlingId);
        userLikePort.save(userLike);
    }

    @Override
    @Transactional
    public void removeLike(Long userId, Long crawlingId) {
        User userLike = userLikePort.findById(new UserMapping(userId, crawlingId))
            .orElseThrow(() -> new LikeException.LikeNotFoundException("좋아요 기록이 존재하지 않습니다: " + crawlingId));
        userLikePort.delete(userLike);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getUserLikes(Long userId) {
        List<Long> crawlingIds = userLikePort.findCrawlingIdsByUserId(userId);
        return articlePort.findByCrawlingIdIn(crawlingIds).stream()
            .map(ArticleResponse::fromEntity)
            .toList();
    }
}
