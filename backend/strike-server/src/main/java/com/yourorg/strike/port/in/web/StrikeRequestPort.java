package com.yourorg.strike.port.in.web;


import java.util.List;

import com.yourorg.strike.adapter.in.dto.DeliveryStrikeDto;

public interface StrikeRequestPort {
    List<DeliveryStrikeDto> getMonthlyStrikes(Long userId, int year, int month); //userId의 월별 스트라이크 조회
    List<DeliveryStrikeDto> getFriendMonthlyStrikes(Long friendId, int year, int month); //friendId의 월별 스트라이크 조회
}