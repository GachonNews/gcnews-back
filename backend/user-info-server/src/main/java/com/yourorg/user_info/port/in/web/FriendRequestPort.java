package com.yourorg.user_info.port.in.web;

import java.util.List;

import com.yourorg.user_info.adapter.out.web.dto.DeliveryFriendDto;

public interface FriendRequestPort {

    List<DeliveryFriendDto> getFriends(Long userId);

    // DeliveryFriendDto addFriend(Long userId, Long friendId);

    DeliveryFriendDto addFriend(Long userId, DeliveryFriendDto friendDto);
    
    DeliveryFriendDto deleteFriend(Long userId, Long friendId);

}