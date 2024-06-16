package com.demo.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.domain.user.UserVo;
import com.demo.domain.user.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class AuthService {
	
	@Autowired
    private UserRepo userRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // Write(Insert, Update, Delete)
    public UserVo signup(UserVo user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");

        UserVo userEntity = userRepository.save(user);
        
		/*
		 * if (Objects.equals(userEntity.getRole(), "ROLE_SELLER")) {
		 * 
		 * } else if (Objects.equals(userEntity.getRole(), "ROLE_USER")) {
		 * 
		 * }
		 */
        
        return userEntity;
    }
    
    public void logout(HttpSession session) {
    	session.invalidate();
    }
}
