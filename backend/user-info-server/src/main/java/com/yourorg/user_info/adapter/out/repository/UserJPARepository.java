package com.yourorg.user_info.adapter.out.repository;

import java.util.Optional;
import com.yourorg.user_info.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserJPARepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
}