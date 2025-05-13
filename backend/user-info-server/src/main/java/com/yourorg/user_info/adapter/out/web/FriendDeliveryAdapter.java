package com.yourorg.user_info.adapter.out.web;

import com.yourorg.user_info.adapter.out.web.dto.DeliveryFriendDto; // Import DeliveryUserDto
import com.yourorg.user_info.domain.entity.Friend;
import com.yourorg.user_info.port.out.web.FriendDeliveryPort; // Import UserDeliveryPort

import lombok.RequiredArgsConstructor; // Import constructor generation
import lombok.extern.slf4j.Slf4j; // Import logging
import org.springframework.stereotype.Component;

@Slf4j // Enable logging
@Component // Mark as a Spring component
@RequiredArgsConstructor
// ✅ **정적 팩토리 메서드 필수**
public class FriendDeliveryAdapter implements FriendDeliveryPort {
    // ✅ **정적 팩토리 메서드 필수**
    @Override
    public void deliverFriendInfo(Friend friend) {
        // DTO → Entity 변환
        DeliveryFriendDto friendDto = new DeliveryFriendDto(friend.getUserId(), friend.getFriendId());
        
        // 로깅 최적화
        log.info("""
            =================================================
            ✅ 요약 정보 전송 (게이트웨이 연결 대기 중)
            📌 유저 ID: {}
            📌 이름: {}
            📌 성별: {}
            📌 나이: {}
            📌 가입일: {}
            =================================================
            """, 
            friendDto.getUserId(),
            friendDto.getFriendId()
        );
    }
    
}
