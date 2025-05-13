package com.yourorg.user_info.domain.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendMapping implements Serializable {

    private Long userId;
    private Long friendId;

    public FriendMapping(){}

    public FriendMapping(Long userId, Long friendId){
        this.userId=userId;
        this.friendId=friendId;
    }

    // getter / setter
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getFriendId() { return friendId; }
    public void setFriendId(Long friendId) { this.friendId = friendId; }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof FriendMapping)) return false;
        FriendMapping that = (FriendMapping) o;
        return Objects.equals(userId, that.userId)&&
                Objects.equals(friendId, that.friendId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userId, friendId);
    }
}
