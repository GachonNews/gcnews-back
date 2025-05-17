package com.yourorg.strike.port.in.web;

import com.yourorg.strike.adapter.in.dto.DeliveryStrikeDto;

public interface UserActivityPort {
    public DeliveryStrikeDto upsertUser(Long userId, DeliveryStrikeDto strike); // userId로 유저 정보 수정
}