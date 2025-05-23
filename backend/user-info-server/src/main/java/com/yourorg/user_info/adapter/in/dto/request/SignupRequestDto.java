package com.yourorg.user_info.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequestDto {
    private String loginId;
    private String password;
    private String name;
    private String email;
}

