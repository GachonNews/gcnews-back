package com.yourorg.user_info.adapter.in.dto.response;


import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.yourorg.user_info.domain.entity.User;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponseDto {
    // private Long userId;
    private String name;
    private String loginId;
    private String password;
    private String email;
    // private String gender;
    // private int age;
    // private String joinAt;
    // private String img;

    public UserResponseDto(String name, String loginId, String password, String email) {
        // this.userId = userId;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }


     public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setName(user.getName());
        dto.setLoginId(user.getLoginId());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        // Map other fields as necessary
        return dto;
    }
    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setLoginId(this.loginId);
        user.setPassword(this.password);
        user.setEmail(this.email);
        // Map other fields as necessary
        return user;
    }
}