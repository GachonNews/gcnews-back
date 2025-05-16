package com.yourorg.strike.port.in.web;


import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;
import java.util.List;

public interface StrikeRequestPort {
    List<DeliveryStrikeDto> getMonthlyStrikes(Long userId, int year, int month); //userId의 월별 스트라이크 조회
    List<DeliveryStrikeDto> getFriendMonthlyStrikes(Long friendId, int year, int month); //friendId의 월별 스트라이크 조회
}