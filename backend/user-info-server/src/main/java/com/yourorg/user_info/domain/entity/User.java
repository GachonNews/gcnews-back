package com.yourorg.user_info.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id")
    private String loginId;

    private String name;
    private String gender;
    private int age;
    @Column(name="join_at")
    private String joinAt;
    @Column(name="user_img")
    @JsonProperty("img")
    private String img;

    @Column(nullable = false)
    private String password; // 비밀번호 필드 추가!

    public User(Long userId, String loginId, String password) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
    }
}
