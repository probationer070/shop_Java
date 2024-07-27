package com.shop.controller.item;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.shop.config.auth.PrincipalDetails;
import com.shop.service.item.ItemService;
import com.shop.vo.Item.ItemVo;
import com.shop.vo.user.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Value("${resources.location}")
	String resourceLocation;
	
	@GetMapping("/itemFix")
	public String itemList(@AuthenticationPrincipal PrincipalDetails principalDetails, 
							Model model) {
		
		String page = null;
		String content = null;
		
		List<ItemVo> items = itemService.TotalitemView();
		model.addAttribute("items", items);
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // ADMIN
			content = "admin/itemFix";
			model.addAttribute("content", content);
			page = "admin/Main";
		} else {
			// USER
			page = "redirect:/main";
		}
		
		return page;
	}
	
	// 상품 등록 페이지
	@GetMapping("/itemNew")
	public String itemSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		String page = null;
		String content = null;
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // ADMIN
			content = "admin/itemForm";
			model.addAttribute("content", content);
			page = "admin/Main";
		} else {
			// USER
			page = "redirect:/main";
		}
		
		return page;
	}
	
	@PostMapping("/itemNewPro")
	public String itemNewPro(ItemVo ivo, 
							@AuthenticationPrincipal PrincipalDetails principalDetails, 
							@RequestPart(value = "imgFile", required = false) MultipartFile imgFile, Model model) {
		String url = null;
		String msg = null;
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
			// ADMIN
			ivo.setUser(principalDetails.getUser());
			ivo.setImgPath(resourceLocation);
			ItemVo r = itemService.saveItem(ivo, imgFile);
			if (r.getUser()!=null) {
				msg = "상품 등록 성공";
			} else {
				msg = "상품 등록 실패";				
			}
			url = "/main";
		} else {
			// USER
			msg = "잘못된 접근입니다.";
			url = "/main";
		}
		model.addAttribute("url", url);
		model.addAttribute("msg", msg);
		
		return "Main/MsgPage";
	}
	
	@GetMapping("/itemModify")
	public String itemModifyForm(@AuthenticationPrincipal PrincipalDetails principalDetails, 
								 Model model, ItemVo ivo) {
		String page = null;
		String content = null;
		ItemVo item = itemService.itemDetail(ivo);
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // ADMIN
			log.info("itemINFO ====> "+item);
			content = "admin/ModifyForm";
			model.addAttribute("item", item);
			model.addAttribute("content", content);
			
			page = "admin/Main";
		} else {
			// USER
			page = "redirect:/main";
		}
		
		return page;
	}
	
	@GetMapping("/itemView")
	public String itemViewNologin(Model model, ItemVo ivo) {
        model.addAttribute("item", itemService.itemDetail(ivo));
		return "/Main/itemView";
	}
	
	@GetMapping("/itemViews")
	public String itemView(Model model, ItemVo ivo,
						   @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
        UserVo user = principalDetails.getUser();
        ItemVo item = itemService.itemDetail(ivo);
        model.addAttribute("item", item);
		
        log.info("user ====> "+user);
		String page = null;
		String content = null;
		if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // ADMIN
			content = "admin/ModifyForm";
			model.addAttribute("user", user);
			model.addAttribute("content", content);
			page = "admin/Main";
		} else {
			content = "user/itemView";
			model.addAttribute("user", user);
			model.addAttribute("content", content);
			page = "user/Main";
		}
		
		return page;
	}
	
	@PostMapping("ItemMgtProc")
	public String productInProc(Model model, ItemVo ivo,
								HttpServletRequest req, HttpServletResponse res,
								@AuthenticationPrincipal PrincipalDetails principalDetails, 
								@RequestPart(value = "imgFile", required = false) MultipartFile imgFile) {
		
		String msg = null;
		String url = null;
		UserVo user = principalDetails.getUser();
		if (user != null && user.getRole().equals("ROLE_ADMIN")) {
			String flag = req.getParameter("flag");
			log.info("flag ====> "+flag);
				if(req.getParameter("flag")!=null) {
					flag = req.getParameter("flag");
					// 업로드를 위한 패스 저장
					if(flag.equals("delete")) {
						int r = itemService.itemDelete(ivo);
						if (r > 0) {
							msg="상품 삭제 성공!";
						} else {
							msg="상품 삭제 실패!";							
						}
					} else if(flag.equals("update")) {
						// update 호출
						//log.info("update ===> ");
						ivo.setUser(principalDetails.getUser());
						ivo.setImgPath(resourceLocation);
						int r = itemService.itemModify(ivo, imgFile);
						if (r > 0) {
							msg="상품 수정 성공!";
						} else {
							msg="상품 수정 실패!";							
						}
					}
					url = "main";
				}
		} else {
			url = "main";	// 최초 화면으로 이동
			msg = "잘못된 접근입니다!";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "Main/MsgPage";
	}
	
}
