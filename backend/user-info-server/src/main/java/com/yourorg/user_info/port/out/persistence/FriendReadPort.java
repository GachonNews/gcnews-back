package com.yourorg.user_info.port.out.persistence;
import com.yourorg.user_info.domain.entity.Friend;

import java.util.List;

public interface FriendReadPort {
    List<Friend> findFriendList(Long userId); //userId의 친구목록 조회
    Friend findDeleteFriend(Long userId, Long friendId); //UserId와 FriendId로 친구 삭제
}