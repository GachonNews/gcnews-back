// UserLikeAdapter.java
package com.yourorg.article.adapter.out.persistence;

import com.yourorg.article.domain.entity.User;
import com.yourorg.article.domain.entity.UserMapping;
import com.yourorg.article.port.out.persistence.UserWritePort;
import com.yourorg.article.adapter.out.repository.UserJPARepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserLikeAdapter implements UserWritePort {

    private final UserJPARepository userLikeRepository;

    public UserLikeAdapter(UserJPARepository userLikeRepository) {
        this.userLikeRepository = userLikeRepository;
    }

    @Override
    public boolean existsByUserIdAndCrawlingId(Long userId, Long crawlingId) {
        return userLikeRepository.existsByUserIdAndCrawlingId(userId, crawlingId);
    }

    @Override
    public void save(User userLike) {
        userLikeRepository.save(userLike);
    }

    @Override
    public Optional<User> findById(UserMapping userLikeId) {
        return userLikeRepository.findById(userLikeId);
    }

    @Override
    public void delete(User userLike) {
        userLikeRepository.delete(userLike);
    }

    @Override
    public List<Long> findCrawlingIdsByUserId(Long userId) {
        return userLikeRepository.findCrawlingIdsByUserId(userId);
    }
}
