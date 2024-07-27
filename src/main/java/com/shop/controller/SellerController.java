//package com.shop.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.shop.config.auth.PrincipalDetails;
//import com.shop.domain.Item.ItemVo;
//import com.shop.domain.user.UserVo;
//import com.shop.service.ItemService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
//@Slf4j
//@Controller
//public class SellerController {
//	
//	@Autowired
//	private ItemService itemService;
//	
//	@Value("${resources.location}")
//	String resourceLocation;
//	
//	// Need Some FeedBack
//	@GetMapping("/itemFix")
//	public String itemMgt(Model model,
//						  @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		UserVo ssKey = null;
//		String page = null;
//		String content = null;
//		if (principalDetails.getUser()!=null) {
//			ssKey = principalDetails.getUser();
//			if (ssKey.getRole().equals("ROLE_ADMIN")) {
//				content = "admin/itemFix";
//				
//				page = "admin/Main";			
//			} else {
//				page = "redirect:/main";				
//			}
//		} else {
//			page = "redirect:/main";
//		}
//		List<ItemVo> items = itemService.TotalitemView();
//		model.addAttribute("items", items);
//		model.addAttribute("content", content);
//		
//		return page;
//	}
//	
//	
//	
//	// 상품 등록
//	@PostMapping("/itemNewpro")
//	public String itemSave(ItemVo item, 
//						   @AuthenticationPrincipal PrincipalDetails principalDetails, 
//						   @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws Exception {
//		
//		String page = null;
//		// String msg = null;
//		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//			if (imgFile == null || imgFile.isEmpty()) {
//				System.out.println("imgTest ==>"+imgFile);
//			}
//			item.setUser(principalDetails.getUser());
//			item.setImgPath(resourceLocation);
//			itemService.saveItem(item, imgFile);
//			page = "redirect:/main";
//		} else {
//			page = "redirect:/main";
//		}
//
//		return page;
//	}
//	
//	// 상품 수정 페이지
//	@GetMapping("/itemModify/{id}")
//	public String itemModifyForm(@AuthenticationPrincipal PrincipalDetails principalDetails, 
//								 @PathVariable("id") int id,
//								 Model model) {
//		String page = null;
//		String content = null;
//		model.addAttribute("item", itemService.itemView(id));
//		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // ADMIN
//			content = "admin/ModifyForm";
//			// model.addAttribute("user", principalDetails.getUser());
//			model.addAttribute("content", content);
//			page = "admin/Main";
//		} else {
//			// USER
//			page = "redirect:/main";
//		}
//		
//		return page;
//	}
//	// 상품 수정 
//	@PostMapping("/itemModifyPro/{id}")
//	public String postMethodName(@AuthenticationPrincipal PrincipalDetails principalDetails, 
//								 Model model, 
//								 ItemVo item,
//			 					 @PathVariable("id") Long id,
//			 					 @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) {
//		String page = null;
//		String content = null;
//		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // ADMIN
//
//			// log.info("id ==> "+id);
//			// UserVo user = itemService.itemView(id).getUser();
//			itemService.itemModify(item, imgFile);
//			
//			// model.addAttribute("user", principalDetails.getUser());
//			
//			page = "redirect:/main";
//		} else {
//			// USER
//			page = "redirect:/main";
//		}
//		
//		return page;
//	}
//	
//	
//	@GetMapping("/itemView/{id}")
//	public String itemViewNologin(Model model, @PathVariable("id") int id) {
//        model.addAttribute("item", itemService.itemView(id));
//		return "/Main/itemView";
//	}
//	
//	
//	// 상품 상세 페이지 All
//	@GetMapping("/itemViews/{id}")
//	public String itemViewDe(
//							 Model model, 
//							 @PathVariable("id") int id,
//							 @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        model.addAttribute("item", itemService.itemView(id));
//		String page = null;
//		String content = null;
//		UserVo user = principalDetails.getUser();
//		if (user!=null) {
//			if (user.getRole().equals("ROLE_ADMIN")) {
//				content = "admin/ModifyForm";
//				model.addAttribute("user", user);
//				model.addAttribute("content", content);
//				page = "admin/Main";				
//			} else {
//				content = "user/itemView";
//				model.addAttribute("user", user);
//				model.addAttribute("content", content);
//				page = "user/Main";				
//			}
//		} else {
//			page = "/Main/itemView";
//		}
//        
//		return page;
//	}
//	
//	
//	@PostMapping("/itemView/{id}")
//	public String itemView(Model model, 
//						   @PathVariable("id") int id, 
//						   @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		
//        UserVo user = principalDetails.getUser();
//
//        model.addAttribute("item", itemService.itemView(id));
//				
//		String page = null;
//		String content = null;
//		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // ADMIN
//			content = "admin/ModifyForm";
//			model.addAttribute("user", user);
//			model.addAttribute("content", content);
//			page = "admin/Main";
//		} else {
//			page = "/Main/itemView";
//		}
//		
//		return page;
//	}
//
//	
//	
//	// 상품 삭제
//	@GetMapping("/itemDelete/{id}")
//	public String itemDelete(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//		    // 판매자
//		    UserVo user = itemService.itemView(id).getUser();
//		
//		    if(user.getId() == principalDetails.getUser().getId()) {
//		        // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 삭제 가능
//		        itemService.itemDelete(id);
//		
//		        return "redirect:/main";
//		    } else {
//		        return "redirect:/main";
//		    }
//		} else {
//		    // 일반 회원이면 거절 -> main
//		    return "redirect:/main";
//		}
//	}
//}
