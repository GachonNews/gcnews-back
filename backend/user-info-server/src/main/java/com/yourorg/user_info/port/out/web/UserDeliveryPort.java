package com.yourorg.user_info.port.out.web;

import com.yourorg.user_info.domain.entity.User; // Import User entity

public interface UserDeliveryPort {
    void deliverUserInfo(User user);
}