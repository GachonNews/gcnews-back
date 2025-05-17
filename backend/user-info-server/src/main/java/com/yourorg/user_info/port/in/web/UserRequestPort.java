package com.yourorg.user_info.port.in.web;

import com.yourorg.user_info.adapter.in.dto.response.UserResponseDto;

public interface UserRequestPort {
    public UserResponseDto getUser(Long userId); // userId로 유저 조회

    public UserResponseDto upsertUser(Long userId, UserResponseDto user);  // 유저 업데이트
}
