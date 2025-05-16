package com.yourorg.user_info.adapter.in.web;

import com.yourorg.user_info.port.in.web.UserRequestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import com.yourorg.user_info.adapter.out.web.dto.DeliveryUserDto;

@RestController
@RequestMapping("/api/user-info/{user_id}/profile")
@RequiredArgsConstructor
public class UserRequestAdapter {
    private final UserRequestPort service;

    @GetMapping
    public ResponseEntity<DeliveryUserDto> getUser(@PathVariable("user_id") Long userId) {
        DeliveryUserDto userDto = service.getUser(userId);
        return userDto== null
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<DeliveryUserDto> upsertUser(
            @PathVariable("user_id") Long userId,
            @RequestBody DeliveryUserDto userDto) {
        DeliveryUserDto dto = service.upsertUser(userId, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
