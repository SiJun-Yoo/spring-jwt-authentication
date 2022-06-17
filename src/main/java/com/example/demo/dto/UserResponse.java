package com.example.demo.dto;

import com.example.demo.domain.User;
import lombok.*;
import org.aspectj.lang.annotation.After;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Setter
public class UserResponse {
    private String userId;
    private String userName;

    public static UserResponse from(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();
    }
}
