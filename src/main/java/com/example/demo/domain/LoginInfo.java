package com.example.demo.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginInfo implements UserDetails {
    private Long number;
    private String userId;
    private String userName;
    private String userPassword;
    private List<GrantedAuthority> roles = new ArrayList<>();

    public LoginInfo(){
        this.number = 0L;
        this.userId = "anonymous";
        this.userName = "anonymous";
        this.userPassword = "secret";
    }

    public LoginInfo(User user){
        this.number = user.getNumber();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        roles.add(new SimpleGrantedAuthority(user.getUserType().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
