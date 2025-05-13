package com.yourorg.user_info.adapter.out.persistence;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yourorg.user_info.adapter.out.repository.FriendJPARepository;
import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.out.persistence.FriendWritePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendWriteAdapter implements FriendWritePort {
    
    private final FriendJPARepository friendJPARepository;
    // Example method to save a friend entity
    @Override
    public Friend saveFriend(Friend friend) {
        System.out.println(friend.getUserId() + friend.getFriendId()+"saveFriendAdapter@@@@@@@@@@@@");
        // Use the repository to save the friend entity
        return friendJPARepository.save(friend);
    }

    @Override
    @Transactional
    public void deleteFriend(Friend friend) {
        // System.out.println(friend.getUserId() + friend.getFriendId()+"deleteFriend@@@@@@@@@@@");
        friendJPARepository.delete(friend);
    }
}