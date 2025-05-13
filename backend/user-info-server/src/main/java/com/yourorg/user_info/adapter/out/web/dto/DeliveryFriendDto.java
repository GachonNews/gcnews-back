package com.yourorg.user_info.adapter.out.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryFriendDto {
    private Long userId;    // 친구 요청을 보낸 유저의 ID
    private Long friendId;  // 친구로 등록된 유저의 ID
    
    public DeliveryFriendDto(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    // JSON 필드 "id"가 들어오면 이 메서드가 호출됩니다.
    @JsonProperty("id")
    private void unpackNestedId(Map<String, Object> id) {
        // Map에서 friendId를 꺼내 Long으로 변환해 필드에 저장
        this.friendId = ((Number) id.get("friendId")).longValue();
    }
}
