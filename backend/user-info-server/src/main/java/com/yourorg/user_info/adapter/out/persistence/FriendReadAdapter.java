package com.yourorg.user_info.adapter.out.persistence;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.out.persistence.FriendReadPort;
import com.yourorg.user_info.adapter.out.repository.FriendJPARepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendReadAdapter implements FriendReadPort {

    private final FriendJPARepository friendRepository;

    @Override
    public List<Friend> findFriendList(Long userId) { //userId의 친구목록 조회
        return friendRepository.findByIdUserId(userId);
    }

    @Override
    public Friend findDeleteFriend(Long userId, Long friendId) { //UserId와 FriendId로 친구 삭제
        Friend existing = friendRepository.
                findByIdUserIdAndIdFriendId(userId, friendId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend not found")
                );
        return existing;
    }
}