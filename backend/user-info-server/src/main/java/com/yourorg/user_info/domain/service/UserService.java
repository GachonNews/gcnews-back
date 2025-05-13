package com.yourorg.user_info.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yourorg.user_info.domain.entity.User;
import com.yourorg.user_info.adapter.out.web.dto.DeliveryUserDto;
import com.yourorg.user_info.port.out.persistence.UserReadPort;
import com.yourorg.user_info.port.out.persistence.UserWritePort;

import lombok.RequiredArgsConstructor;

import com.yourorg.user_info.port.in.web.UserRequestPort;


@Service
@RequiredArgsConstructor
public class UserService implements UserRequestPort {
    private final UserReadPort  readPort;
    private final UserWritePort writePort;

    @Override
    public DeliveryUserDto getUser(Long userId) {
        User u = readPort.findUser(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return DeliveryUserDto.fromEntity(u);
    }

    @Override
    @Transactional
    public DeliveryUserDto upsertUser(Long userId, DeliveryUserDto dto) {
        User saved = readPort.findUser(userId)
            .map(existing -> {
                // 업데이트
                existing.setName(dto.getName());
                existing.setGender(dto.getGender());
                existing.setAge(dto.getAge());
                existing.setJoinAt(dto.getJoinAt());
                existing.setImg(dto.getImg());
                // …
                return writePort.saveUser(existing);
            })
            .orElseGet(() -> {
                // 신규
                User newUser = dto.toEntity();
                newUser.setUserId(userId);
                return writePort.saveUser(newUser);
            });
        return DeliveryUserDto.fromEntity(saved);
    }
}