package com.yourorg.user_info.port.out.persistence;

import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserReadPort {
    Optional<User> findUser(Long userId); // userId로 유저 조회
    Optional<User> findFriend(Long friendId); //friendId에 해당하는 유저정보 반환
    List<Friend> findFriendList(Long userId); // userId의 친구 목록 조회
    Friend findDeleteFriend(Long userId, Long friendId); // userId와 friendId로 삭제할 친구 조회
}
