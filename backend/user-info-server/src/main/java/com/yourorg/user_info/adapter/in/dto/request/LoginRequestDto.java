package com.yourorg.user_info.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    private String loginId;
    private String password;
}
