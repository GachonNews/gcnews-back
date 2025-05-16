package com.yourorg.user_info.adapter.in.web;

import com.yourorg.user_info.port.in.web.FriendRequestPort;
import com.yourorg.user_info.adapter.out.web.dto.DeliveryFriendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/user-info/{user_id}/friend")
@RequiredArgsConstructor
public class FriendRequestAdapter {
    private final FriendRequestPort service;

    @GetMapping
    public ResponseEntity<List<DeliveryFriendDto>> getFriends(@PathVariable("user_id") Long userId) {
        List<DeliveryFriendDto> list = service.getFriends(userId);
        return list.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<DeliveryFriendDto> addFriend(
            @PathVariable("user_id") Long userId,
            @RequestBody DeliveryFriendDto friendDto) {
        DeliveryFriendDto dto = service.addFriend(userId, friendDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{friend_id}")
    public ResponseEntity<DeliveryFriendDto> deleteFriend(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId) {
        DeliveryFriendDto dto = service.deleteFriend(userId, friendId);
        return ResponseEntity.ok(dto);
    }
}
