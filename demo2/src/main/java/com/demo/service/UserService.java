package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.user.UserRepo;
import com.demo.domain.user.UserVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	@Autowired
	private final UserRepo userRepo;


	public UserVo findUser(int i) {
		return userRepo.findById(i).get();
	}

}
