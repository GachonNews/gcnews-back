package com.yourorg.user_info.adapter.in.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class FriendResponseDto {
    private Long userId;  // 친구를 추가한 유저의 ID
    private Long friendId;  // 친구로 등록된 유저의 ID

    public FriendResponseDto(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
