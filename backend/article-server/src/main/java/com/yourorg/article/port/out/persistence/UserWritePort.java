// UserLikePort.java
package com.yourorg.article.port.out.persistence;

import com.yourorg.article.domain.entity.User;
import com.yourorg.article.domain.entity.UserMapping;
import java.util.List;
import java.util.Optional;

public interface UserWritePort {
    boolean existsByUserIdAndCrawlingId(Long userId, Long crawlingId);
    void save(User userLike);
    Optional<User> findById(UserMapping userLikeId);
    void delete(User userLike);
    List<Long> findCrawlingIdsByUserId(Long userId);
}
