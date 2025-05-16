package com.yourorg.user_info.adapter.out.repository;

import com.yourorg.user_info.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthJPARepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
}
