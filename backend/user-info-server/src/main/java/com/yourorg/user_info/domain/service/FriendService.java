package com.yourorg.user_info.domain.service;

import org.springframework.stereotype.Service;

import com.yourorg.user_info.adapter.out.web.dto.DeliveryFriendDto;
import com.yourorg.user_info.port.out.persistence.FriendReadPort;
import com.yourorg.user_info.port.out.persistence.FriendWritePort;

import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.in.web.FriendRequestPort;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService implements FriendRequestPort {
    private final FriendReadPort readPort;
    private final FriendWritePort writePort;

    @Override
    public List<DeliveryFriendDto> getFriends(Long userId) {
        return readPort.findFriendList(userId).stream()
                .map(f -> new DeliveryFriendDto(f.getUserId(), f.getFriendId()))
                .toList();
    }

    // @Override
    // public DeliveryFriendDto addFriend(Long userId, Long friendId) {
    //     System.out.println(userId + friendId+"addFriend!!!!!!!!!!!!");
    //     Friend f = new Friend(userId, friendId);
    //     Friend saved = writePort.saveFriend(f);
    //     return new DeliveryFriendDto(saved.getUserId(), saved.getFriendId());
    // }
 @Override
    public DeliveryFriendDto addFriend(Long userId, DeliveryFriendDto friendDto) {
        Long friendId = friendDto.getFriendId();
        Friend f = new Friend(userId, friendId);
        Friend saved = writePort.saveFriend(f);
        return new DeliveryFriendDto(saved.getUserId(), saved.getFriendId());
    }
    @Override
    @Transactional
    public DeliveryFriendDto deleteFriend(Long userId, Long friendId) {
        Friend existing = readPort.findDeleteFriend(userId, friendId);
        writePort.deleteFriend(existing);
        return new DeliveryFriendDto(existing.getUserId(), existing.getFriendId());
        
    }

}
