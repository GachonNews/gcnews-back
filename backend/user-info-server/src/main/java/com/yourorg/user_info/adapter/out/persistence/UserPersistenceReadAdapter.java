package com.yourorg.user_info.adapter.out.persistence;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.out.persistence.UserReadPort;
import com.yourorg.user_info.adapter.out.repository.UserJPARepository;
import com.yourorg.user_info.adapter.out.repository.FriendJPARepository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 * User, Friend 조회에 대한 Persistence Adapter
 * (Read 관련 비즈니스만 담당)
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceReadAdapter implements UserReadPort {

    private final UserJPARepository userRepository;
    private final FriendJPARepository friendRepository;

    // User 읽기
    @Override
    public Optional<User> findUser(Long userId) {
        return userRepository.findByUserId(userId);
    }

    // Friend 목록 읽기
    @Override
    public List<Friend> findFriendList(Long userId) {
        return friendRepository.findByIdUserId(userId);
    }

    // Friend 삭제용 조회
    @Override
    public Friend findDeleteFriend(Long userId, Long friendId) {
        return friendRepository
            .findByIdUserIdAndIdFriendId(userId, friendId)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend not found")
            );
    }
}
