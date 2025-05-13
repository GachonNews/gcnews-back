package com.yourorg.user_info.domain.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@Table(name = "Users") //User객체를 저장할 테이블의 이름, SQL쿼리 중 user가 존재해서 충돌을 방지하기 위해 users로 설정함.
public class User {

    public User(){}
    public User(String name, String gender, int age, String joinAt, String img) {
        this.name = name;
        this.gender=gender;
        this.age=age;
        this.joinAt=joinAt;
        this.img=img;
    }

    @Id //User 객체는 id변수로 식별한다는 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") //user_id라는 이름으로 DB에 저장
    private Long userId;

    private String name;
    private String gender;
    private int age;
    @Column(name="join_at")
    private String joinAt;
    
    @Column(name="user_img")
    @JsonProperty("img")
    private String img; //유저의 프로필사진

}