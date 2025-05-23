package com.yourorg.user_info.domain.service;

import org.springframework.stereotype.Service;

import com.yourorg.user_info.port.out.persistence.UserReadPort;
import com.yourorg.user_info.port.out.persistence.UserWritePort;

import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.adapter.in.dto.response.FriendResponseDto;
import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.in.web.FriendRequestPort;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService implements FriendRequestPort {
    private final UserReadPort readPort;      // 읽기 전용 Port
    private final UserWritePort writePort;    // 쓰기 전용 Port

    @Override
    public List<FriendResponseDto> getFriends(Long userId) {
        // userId의 친구 목록을 조회해서 FriendResponseDto 리스트로 반환
        return readPort.findFriendList(userId).stream()
                .map(f -> new FriendResponseDto(f.getUserId(), f.getFriendId()))
                .toList();
    }

    @Override
    public FriendResponseDto addFriend(Long userId, FriendResponseDto friendDto) {
        // userId와 friendId로 새로운 Friend 생성 후 저장
        Long friendId = friendDto.getFriendId();
        Friend f = new Friend(userId, friendId);
        Friend saved = writePort.saveFriend(f);
        // 저장된 결과를 FriendResponseDto로 반환
        return new FriendResponseDto(saved.getUserId(), saved.getFriendId());
    }

    @Override
    @Transactional
    public FriendResponseDto deleteFriend(Long userId, Long friendId) {
        // 삭제할 친구 정보를 조회
        Friend existing = readPort.findDeleteFriend(userId, friendId);
        writePort.deleteFriend(existing); // 친구 삭제
        // 삭제된 친구 정보를 FriendResponseDto로 반환
        return new FriendResponseDto(userId, existing.getFriendId());
    }
}
