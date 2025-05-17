package com.yourorg.user_info.port.in.web;

import java.util.List;

import com.yourorg.user_info.adapter.in.dto.response.FriendResponseDto;

public interface FriendRequestPort {

    List<FriendResponseDto> getFriends(Long userId); // userId로 친구 목록 조회

    FriendResponseDto addFriend(Long userId, FriendResponseDto friendDto); // userId로 친구 추가
    
    FriendResponseDto deleteFriend(Long userId, Long friendId); // userId로 친구 삭제

}