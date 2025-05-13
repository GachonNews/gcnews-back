package com.yourorg.user_info.port.out.persistence;

// Import User entity
import com.yourorg.user_info.domain.entity.Friend;

public interface FriendWritePort {
    Friend saveFriend(Friend friend);
    void deleteFriend(Friend friend); 
}