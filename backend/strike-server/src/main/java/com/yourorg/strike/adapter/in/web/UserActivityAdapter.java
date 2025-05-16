package com.yourorg.strike.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourorg.strike.port.in.web.UserActivityPort;

import lombok.RequiredArgsConstructor;

import com.yourorg.strike.adapter.out.web.dto.DeliveryStrikeDto;

@RestController
@RequestMapping("/api/recent")
@RequiredArgsConstructor
public class UserActivityAdapter{


    private final UserActivityPort service;

    @PostMapping
    public ResponseEntity<DeliveryStrikeDto> upsertUser(
            @RequestBody DeliveryStrikeDto strikeDto) {
        DeliveryStrikeDto dto = service.upsertUser(strikeDto.getUserId(), strikeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
}
