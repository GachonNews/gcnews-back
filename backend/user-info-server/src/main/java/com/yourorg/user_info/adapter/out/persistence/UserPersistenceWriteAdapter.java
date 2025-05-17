package com.yourorg.user_info.adapter.out.persistence;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.adapter.out.repository.UserJPARepository;
import com.yourorg.user_info.adapter.out.repository.FriendJPARepository;
import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.out.persistence.UserWritePort;

@Component
@RequiredArgsConstructor
public class UserPersistenceWriteAdapter implements UserWritePort {

    private final UserJPARepository userJPARepository;
    private final FriendJPARepository friendJPARepository;

    // User 저장
    @Override
    public User saveUser(User user) {
        return userJPARepository.save(user);
    }

    // Friend 저장
    @Override
    public Friend saveFriend(Friend friend) {
        return friendJPARepository.save(friend);
    }

    // Friend 삭제
    @Override
    @Transactional
    public void deleteFriend(Friend friend) {
        friendJPARepository.delete(friend);
    }
}
