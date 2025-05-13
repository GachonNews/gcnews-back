package com.yourorg.user_info.port.out.web;

import com.yourorg.user_info.domain.entity.Friend; // Import User entity

public interface FriendDeliveryPort {
    void deliverFriendInfo(Friend friend); // Method to deliver friend information
}