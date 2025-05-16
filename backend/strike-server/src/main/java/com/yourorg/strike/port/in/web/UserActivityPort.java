package com.yourorg.strike.port.in.web;

import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;

public interface UserActivityPort {
    public DeliveryStrikeDto upsertUser(Long userId, DeliveryStrikeDto strike); // ✅ 컨트롤러 역할)
}