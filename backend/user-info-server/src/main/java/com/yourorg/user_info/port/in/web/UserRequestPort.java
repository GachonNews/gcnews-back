package com.yourorg.user_info.port.in.web;

import com.yourorg.user_info.adapter.out.web.dto.DeliveryUserDto;

public interface UserRequestPort {
    public DeliveryUserDto getUser(Long userId);

    public DeliveryUserDto upsertUser(Long userId, DeliveryUserDto user); // ✅ 컨트롤러 역할)
}
