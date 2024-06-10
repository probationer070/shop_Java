package com.demo.dto;

import com.demo.domain.user.User;

import lombok.Data;

@Data
public class signupDto {
    private String username;
    private String password;
    private String email;
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .username(name)
                .build();
    }
}