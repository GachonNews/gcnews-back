package com.yourorg.strike.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.yourorg.strike.port.in.web.StrikeRequestPort;
import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;
import java.util.List;

@RestController
@RequestMapping("/api/{user_id}/attendance")
@RequiredArgsConstructor
public class StrikeRequestAdapter {

    private final StrikeRequestPort service;

    @GetMapping
    public ResponseEntity<List<DeliveryStrikeDto>> getMonthlyStrikes(
            @PathVariable("user_id") Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return service.getMonthlyStrikes(userId, year, month).isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(service.getMonthlyStrikes(userId, year, month));
    }
    @GetMapping("/{friend_id}")
    public ResponseEntity<List<DeliveryStrikeDto>> getFriendMonthlyStrikes(
           @PathVariable("user_id")   Long userId,
            @PathVariable("friend_id") Long friendId,
            @RequestParam("year")      int year,
            @RequestParam("month")     int month) {
        return service.getFriendMonthlyStrikes(friendId, year, month).isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(service.getFriendMonthlyStrikes(friendId, year, month));
    }
    
}
