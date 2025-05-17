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
    private String gender;
    private int age;
    private String joinAt;
    private String img;

    public UserResponseDto(String name, String gender, int age, String joinAt, String img) {
        // this.userId = userId;
        this.name = name;
        this.gender=gender;
        this.age=age;
        this.joinAt=joinAt;
        this.img=img;
    }


     public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setName(user.getName());
        dto.setGender(user.getGender());
        dto.setAge(user.getAge());
        dto.setJoinAt(user.getJoinAt());
        dto.setImg(user.getImg());
        // Map other fields as necessary
        return dto;
    }
    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setGender(this.gender);
        user.setAge(this.age);
        user.setJoinAt(this.joinAt);
        user.setImg(this.img);
        // Map other fields as necessary
        return user;
    }
}