package com.demo.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.config.auth.PrincipalDetails;
import com.demo.domain.Item.ItemVo;
import com.demo.domain.cart.CartVo;
import com.demo.domain.cartItem.CartItemVo;
import com.demo.domain.user.UserVo;
import com.demo.service.CartService;
import com.demo.service.ItemService;
import com.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CartService cartService;
	
	@PostMapping("/addCartItem/{id}")
	public String addCartItem(@PathVariable("id") int id,
							  @PathVariable("itemId") int itemId,
							  int amount) {
		UserVo user = userService.findUser(id);
		ItemVo item = itemService.itemView(itemId);
		
		cartService.addCart(user, item, amount);

		return "redirect:/itemView/{id}";
	}
	
	 // 장바구니 페이지 접속
    @GetMapping("/cart/{id}")
    public String userCartPage(Model model, 
    						@PathVariable("id") int id,
    						@AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 장바구니에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            UserVo user = userService.findUser(id);
            log.info("user info ===> "+user);
            
            //log.info("user info ===> "+user);
            // 로그인 되어 있는 유저에 해당하는 장바구니 가져오기
            CartVo userCart = user.getCart();

            // 장바구니에 들어있는 아이템 모두 가져오기
            List<CartItemVo> cartItemList = cartService.allUserCartView(userCart);

            // 장바구니에 들어있는 상품들의 총 가격
            int totalPrice = 0;
            for (CartItemVo cartitem : cartItemList) {
                totalPrice += cartitem.getCount() * cartitem.getItem().getItem_price();
            }

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
            model.addAttribute("user", userService.findUser(id));

            return "/user/Main";
        }
        // 로그인 id와 장바구니 접속 id가 같지 않는 경우
        else {
            return "redirect:/main";
        }
    }
}
