package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.config.auth.PrincipalDetails;
import com.shop.service.item.ItemService;
import com.shop.vo.Item.ItemVo;
import com.shop.vo.user.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/")	// 로그인 안 한 유저
	public String nice(HttpServletRequest req, 
					   HttpServletResponse res, Model model) {
		List<ItemVo> items = itemService.TotalitemView();
		model.addAttribute("items", items);
		
		String page = null;
		HttpSession session = req.getSession();
		if (session.getAttribute("ssKey")!=null) {
			
			page = "redirect:main";				
			
		} else {
			page = "layouts/HomePage";
		}
		
		return page;
	}
	
	// 메인 페이지 (로그인 유저) - ADMIN, USER
	@GetMapping("/main")
	public String Home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		List<ItemVo> items = itemService.TotalitemView();
		model.addAttribute("items", items);
		String page = null;
		String content = null;
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // ADMIN
			content = "user/Home";
			model.addAttribute("user", principalDetails.getUser());
			model.addAttribute("content", content);
			page = "admin/Main";
		} else if (principalDetails.getUser().getRole().equals("ROLE_USER")) {
			// USER
			content = "user/Home";
			model.addAttribute("user", principalDetails.getUser());
			model.addAttribute("content", content);
			page = "user/Main";
		}
		
		return page;
	}
	
	
	
	
}
	