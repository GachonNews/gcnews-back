package com.yourorg.article.domain.service.web;

import com.yourorg.article.adapter.in.web.dto.response.ArticleResponse;
import com.yourorg.article.domain.entity.User;
import com.yourorg.article.domain.entity.UserMapping;
import com.yourorg.article.port.in.web.ArticleLikeApiPort;
import com.yourorg.article.port.out.persistence.UserWritePort;
import com.yourorg.article.port.out.persistence.ArticleFindPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleLikeApiService implements ArticleLikeApiPort {

    private final UserWritePort userLikePort;
    private final ArticleFindPort articlePort;

    @Override
    @Transactional
    public boolean addLike(Long userId, Long crawlingId) {
        if (userLikePort.existsByUserIdAndCrawlingId(userId, crawlingId)) {
            // 이미 좋아요가 존재! -> 실패 (중복)
            return false;
        }
        User userLike = new User();
        userLike.setUserId(userId);
        userLike.setCrawlingId(crawlingId);
        userLikePort.save(userLike);
        return true;
    }

    @Override
    @Transactional
    public boolean removeLike(Long userId, Long crawlingId) {
        Optional<User> userLikeOpt = userLikePort.findById(new UserMapping(userId, crawlingId));
        if (userLikeOpt.isEmpty()) {
            // 좋아요 기록 없음 -> 실패
            return false;
        }
        userLikePort.delete(userLikeOpt.get());
        return true;
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
