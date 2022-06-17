package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Column(unique = true)
    private String userId;

    private String userPassword;

    private String userName;

    private UserType userType;

    @Builder
    public User(String userId, String userPassword, String userName, UserType userType){
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userType = userType;
    }
}
