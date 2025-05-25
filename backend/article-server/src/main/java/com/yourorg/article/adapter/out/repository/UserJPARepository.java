package com.yourorg.article.adapter.out.repository;

import com.yourorg.article.domain.entity.User;
import com.yourorg.article.domain.entity.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserJPARepository extends JpaRepository<User, UserMapping> {
    boolean existsByUserIdAndCrawlingId(Long userId, Long crawlingId);
    @Query("SELECT u.crawlingId FROM User u WHERE u.userId = :userId")
    List<Long> findCrawlingIdsByUserId(Long userId);
}
