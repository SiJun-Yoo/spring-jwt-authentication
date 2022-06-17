package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserResponse findById(Long number){
        User user = userRepository.findById(number).orElseThrow(()->new IllegalArgumentException("잘못된 회원 번호 입니다."));
        return UserResponse.from(user);
    }
}
