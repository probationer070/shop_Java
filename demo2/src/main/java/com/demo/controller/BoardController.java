package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.demo.config.auth.PrincipalDetails;
import com.demo.domain.Item.ItemVo;
import com.demo.domain.user.UserVo;
import com.demo.service.ItemService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/")	// 로그인 안 한 유저
	public String nice(HttpServletRequest req, 
					   HttpServletResponse res, Model model) {
		List<ItemVo> items = itemService.TotalitemView();
		model.addAttribute("items", items);
		
		UserVo ssKey = null;
		String page = null;
		HttpSession session = req.getSession();
		if (session.getAttribute("ssKey")!=null) {
			ssKey = (UserVo) session.getAttribute("ssKey");
			if (ssKey.getRole().equals("ROLE_ADMIN")) {
				page = "redirect:main";				
			} else {
				page = "redirect:main";				
			}
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
			content = "admin/Home";
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
	