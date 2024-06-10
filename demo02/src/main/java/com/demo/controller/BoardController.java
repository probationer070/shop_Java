package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.demo.config.auth.PrincipalDetails;
import com.demo.domain.Item.Item;
import com.demo.domain.user.User;
import com.demo.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/")
	public String nice(Model model) {
		List<Item> items = itemService.TotalitemView();
		model.addAttribute("items", items);
		
		return "/main";
	}
	
	@GetMapping("/main")
	public String Home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		List<Item> items = itemService.TotalitemView();
		model.addAttribute("items", items);
	
		if(principalDetails.getUser().getRole().equals("ADMIN")) {
            // ADMIN
			model.addAttribute("user", principalDetails.getUser());
		} else {
			model.addAttribute("user", principalDetails.getUser());
		}
		
		return "/Main/Homepage";
	}
	
	// 상품 등록 페이지
	@GetMapping("/item/new")
	public String itemSaveForm() {
		return "/admin/itemForm";
	}
	
	// 상품 등록
	@PostMapping("/item/new/pro")
	public String itemSave(Item item, 
						   @AuthenticationPrincipal PrincipalDetails principalDetails, 
						   @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws Exception {
		if (imgFile == null || imgFile.isEmpty()) {
			System.out.println("imgTest ==>"+imgFile);
		}
		item.setUser(principalDetails.getUser());
		itemService.saveItem(item, imgFile);
		
		return "redirect:/main";
	}
	
	// 상품 수정 페이지
	@GetMapping("/item/modify/{id}")
	public String itemModifyForm() {
		return "/admin/ModifyForm";
	}
	// 상품 수정 
	
	// 상품 상세 페이지
	@GetMapping("/item/view/{id}")
	public String itemView(Model model, @PathVariable("id") int id, 
							@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
        User user = principalDetails.getUser();

        model.addAttribute("item", itemService.itemView(id));
        model.addAttribute("user", user);
		
		return "/Main/itemView";
	}
	// 상품 삭제
	@PostMapping("/item/delete/{id}")
	public String itemDelete(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
		    // 판매자
		    User user = itemService.itemView(id).getUser();
		
		    if(user.getId() == principalDetails.getUser().getId()) {
		        // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 삭제 가능
		        itemService.itemDelete(id);
		
		        return "redirect:/main";
		    } else {
		        return "redirect:/main";
		    }
		} else {
		    // 일반 회원이면 거절 -> main
		    return "redirect:/main";
		    }
		}
	
	
}
	