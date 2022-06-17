package com.example.demo.service;

import com.example.demo.domain.LoginInfo;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginInfoService {
    private final UserRepository userRepository;
    public LoginInfo loadUserByUserId(String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new LoginInfo(user);
    }
}
