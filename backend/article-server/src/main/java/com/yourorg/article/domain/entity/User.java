package com.yourorg.article.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
@IdClass(UserMapping.class)
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;
    
    @Id
    @Column(name = "crawling_id") // ✅ PK 매핑 (1차 원인)
    private Long crawlingId;
}
