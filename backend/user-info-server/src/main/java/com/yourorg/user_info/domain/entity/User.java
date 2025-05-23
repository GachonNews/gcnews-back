package com.yourorg.user_info.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Users")
public class User {

    public User(String loginId, String password, String name, String email){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id")
    private String loginId;

    @Column(nullable = false)
    private String password; // 비밀번호 필드 추가!

    private String name;

    private String email;

    // private String gender;
    // private int age;
    // @Column(name="join_at")
    // private String joinAt;
    // @Column(name="user_img")
    // @JsonProperty("img")
    // private String img;
}
