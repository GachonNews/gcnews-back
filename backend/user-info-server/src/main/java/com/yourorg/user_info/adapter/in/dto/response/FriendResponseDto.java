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
    private String friendLoginId;  // 친구의 로그인 ID (선택적, 필요시 추가 가능)

    public FriendResponseDto(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
    public FriendResponseDto(Long userId, String friendLoginId) {
        this.userId = userId;
        this.friendLoginId = friendLoginId;
    }
}
