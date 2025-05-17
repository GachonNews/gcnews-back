package com.yourorg.user_info.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourorg.user_info.adapter.in.dto.response.UserResponseDto;
import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.port.out.persistence.UserReadPort;
import com.yourorg.user_info.port.out.persistence.UserWritePort;
import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.port.in.web.UserRequestPort;

@Service
@RequiredArgsConstructor
public class UserService implements UserRequestPort {
    private final UserReadPort  readPort;   // 읽기 전용 Port
    private final UserWritePort writePort;  // 쓰기 전용 Port

    @Override
    public UserResponseDto getUser(Long userId) {
        // userId로 유저 조회, 없으면 예외 발생
        User u = readPort.findUser(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // User 엔티티를 Response DTO로 변환해서 반환
        return UserResponseDto.fromEntity(u);
    }

    @Override
    @Transactional
    public UserResponseDto upsertUser(Long userId, UserResponseDto dto) {
        // userId로 유저를 찾아 있으면 업데이트, 없으면 신규 등록
        User saved = readPort.findUser(userId)
            .map(existing -> {
                // 기존 정보 업데이트
                existing.setName(dto.getName());
                existing.setGender(dto.getGender());
                existing.setAge(dto.getAge());
                existing.setJoinAt(dto.getJoinAt());
                existing.setImg(dto.getImg());
                // 기타 필드 업데이트
                return writePort.saveUser(existing); // 변경 저장
            })
            .orElseGet(() -> {
                // 유저가 없으면 신규 생성
                User newUser = dto.toEntity();
                newUser.setUserId(userId); // ID 설정
                return writePort.saveUser(newUser); // 새 유저 저장
            });
        // 저장된 엔티티를 Response DTO로 변환해서 반환
        return UserResponseDto.fromEntity(saved);
    }
}
