package com.yourorg.user_info.adapter.out.web;

import com.yourorg.user_info.adapter.out.web.dto.DeliveryUserDto; // Import DeliveryUserDto
import com.yourorg.user_info.domain.entity.User; // Import User entity
import com.yourorg.user_info.port.out.web.UserDeliveryPort; // Import UserDeliveryPort

import lombok.RequiredArgsConstructor; // Import constructor generation
import lombok.extern.slf4j.Slf4j; // Import logging
import org.springframework.stereotype.Component;

@Slf4j // Enable logging
@Component // Mark as a Spring component
@RequiredArgsConstructor
public class UserDeliveryAdapter implements UserDeliveryPort {
    @Override
    public void deliverUserInfo(User user) {
        // DTO → Entity 변환
        // ✅ **정적 팩토리 메서드 필수**
        DeliveryUserDto userDto = new DeliveryUserDto(
            // user.getUserId(),
            user.getName(),
            user.getGender(),
            user.getAge(),
            user.getJoinAt(),
            user.getImg()
        );


        
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
            // userDto.getUserId(),
            userDto.getName(),
            userDto.getGender(),
            userDto.getAge(),
            userDto.getJoinAt(),
            userDto.getImg()
        // ✅ **정적 팩토리 메서드 필수**
        );
    }
}