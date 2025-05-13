package com.yourorg.user_info.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Friends")
public class Friend {


    @EmbeddedId
    private FriendMapping id;

    public Friend(){}
    public Friend(Long userId, Long friendId) {
        this.id = new FriendMapping(userId, friendId);
    }

    // Optional: 편의를 위한 transient getter
    @Transient public Long getUserId()   { return id.getUserId();   }
    @Transient public Long getFriendId() { return id.getFriendId(); }

}