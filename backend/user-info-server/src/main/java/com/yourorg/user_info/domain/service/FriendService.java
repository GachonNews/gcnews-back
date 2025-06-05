package com.yourorg.user_info.domain.service;

import org.springframework.stereotype.Service;

import com.yourorg.user_info.port.out.persistence.UserReadPort;
import com.yourorg.user_info.port.out.persistence.UserWritePort;

import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.exception.UserNotFoundException;
import com.yourorg.user_info.adapter.in.dto.response.UserResponseDto;
import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.adapter.in.dto.response.FriendResponseDto;
import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.in.web.FriendRequestPort;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService implements FriendRequestPort {
    private final UserReadPort readPort;      // 읽기 전용 Port
    private final UserWritePort writePort;    // 쓰기 전용 Port


@Override
    public UserResponseDto getFriend(Long userId) {
        // userId로 유저 조회, 없으면 예외 발생
        User u = readPort.findUser(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // User 엔티티를 Response DTO로 변환해서 반환
        return UserResponseDto.fromEntity(u);
    }
    
    @Override
    public List<FriendResponseDto> getFriends(Long userId) {
        // userId의 친구 목록을 조회해서 FriendResponseDto 리스트로 반환
        return readPort.findFriendList(userId).stream()
                .map(f -> new FriendResponseDto(f.getUserId(), f.getFriendId()))
                .toList();
    }

    @Override
    public FriendResponseDto addFriend(Long userId, String loginId) {
        // 1. Optional 처리해서 값이 없으면 null 반환
        Optional<User> friendOpt = readPort.findUser(loginId);
        if (friendOpt.isEmpty()) {
            return null; // <-- 친구가 없으면 null 반환
        }

        User friendUser = friendOpt.get();
        // 2. Friend 생성 및 저장
        Friend newFriend = new Friend(userId, friendUser.getUserId());
        Friend saved = writePort.saveFriend(newFriend);

        // 3. DTO 반환
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
