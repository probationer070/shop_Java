package com.demo.dto;

import com.demo.domain.user.UserVo;

import lombok.Data;

@Data
public class signupDto {
    private String username;
    private String password;
    private String email;
    private String name;

    public UserVo toEntity() {
        return UserVo.builder()
                .username(username)
                .password(password)
                .email(email)
                .username(name)
                .build();
    }
}