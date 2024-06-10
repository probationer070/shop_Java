package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.domain.user.User;
import com.demo.dto.signupDto;
import com.demo.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/signin")
	public String signin() {
		return "Main/signin";
	}

	@GetMapping("/signup")
	public String signup() {
		return "Main/signup";
	}

	
	@PostMapping("/signup")
    public String signUpPost(signupDto signupDto) {
        User user = signupDto.toEntity(); //새로운 유저 받음
        
        User userEntity = authService.signup(user);
        log.info("newUser ==> " + userEntity);

        return "Main/signin";
    }

    // 로그인성공 창에서 로그아웃 버튼
    //@RequestMapping(value="logout", method = RequestMethod.GET)
    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        authService.logout(session);
        return "redirect:/signin";
    }
	
	
}
