package com.yourorg.article.domain.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserMapping implements Serializable {
    private Long userId;
    private Long crawlingId;
    
    // ✅ 필수 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMapping)) return false;
        UserMapping that = (UserMapping) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(crawlingId, that.crawlingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, crawlingId);
    }
}
