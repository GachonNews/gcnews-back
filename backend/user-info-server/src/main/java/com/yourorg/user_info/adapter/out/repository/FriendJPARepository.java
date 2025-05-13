package com.yourorg.user_info.adapter.out.repository;

import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.domain.entity.FriendMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface FriendJPARepository extends JpaRepository<Friend, FriendMapping> {
    List<Friend> findByIdUserId(Long userId); //userId의 친구목록 조회
    Optional<Friend> findByIdUserIdAndIdFriendId(Long userId, Long friendId); //UserId와 FriendId로 친구 삭제
}