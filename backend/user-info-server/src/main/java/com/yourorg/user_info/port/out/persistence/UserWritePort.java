package com.yourorg.user_info.port.out.persistence;

import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.domain.entity.User;

public interface UserWritePort {
    User saveUser(User user); // 유저 정보 저장
    Friend saveFriend(Friend friend); // 친구 정보 저장
    void deleteFriend(Friend friend); // 친구 삭제
}
