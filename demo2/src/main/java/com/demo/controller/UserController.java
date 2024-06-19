package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.config.auth.PrincipalDetails;
import com.demo.domain.Item.ItemVo;
import com.demo.domain.cart.CartVo;
import com.demo.domain.cartItem.CartItemVo;
import com.demo.domain.orderItem.OrderItem;
import com.demo.domain.user.UserVo;
import com.demo.service.CartService;
import com.demo.service.ItemService;
import com.demo.service.OrderService;
import com.demo.service.UserService;

import jakarta.transaction.Transactional;
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
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/addCartItem/{id}/{itemId}")
	public String addCartItem(@PathVariable("id") int id,
							  @PathVariable("itemId") int itemId,
							  @RequestParam("amount") int amount,
							  Model model) {
		UserVo user = userService.findUser(id);
		ItemVo item = itemService.itemView(itemId);
		log.info("item ====> "+ item);
		cartService.addCart(user, item, amount);
		// model.addAttribute("itemid", itemId);

		return "redirect:/itemViews/{itemId}";
	}
	
	// 장바구니 페이지 접속
    @GetMapping("/cart/{id}")
    public String userCartPage(Model model, 
    						@PathVariable("id") int id,
    						@AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 장바구니에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            UserVo user = userService.findUser(id);
            log.info("user info =====> "+user);
            
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
            
            model.addAttribute("content", "user/UserCart");
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
            model.addAttribute("user", user);

            return "user/Main";
        }
        // 로그인 id와 장바구니 접속 id가 같지 않는 경우
        else {
            return "redirect:/main";
        }
    }
    
    @GetMapping("/deleteItem/{id}/{itemId}")
    public String deleteCartItem(@PathVariable("id") int id, 
    							 @PathVariable("itemId") int itemId, 
    							 Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    	if (principalDetails.getUser().getId() == id) {
            // itemId로 장바구니 상품 찾기
            CartItemVo cartItem = cartService.findCartItemById(itemId);
            // 해당 유저의 카트 찾기
            CartVo userCart = cartService.findUserCart(id);

            userCart.setCount(userCart.getCount()-cartItem.getCount());
            cartService.cartItemDelete(itemId);

            // 해당 유저의 장바구니 상품들
            List<CartItemVo> cartItemList = cartService.allUserCartView(userCart);

            // 총 가격 += 수량 * 가격
            int totalPrice = 0;
            for (CartItemVo cartitem : cartItemList) {
                totalPrice += cartitem.getCount() * cartitem.getItem().getItem_price();
            }
            model.addAttribute("content", "user/UserCart");
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
            model.addAttribute("user", userService.findUser(id));

            return "redirect:/main";
        } else {
            return "redirect:/main";
        }

    }
    
    @Transactional
    @PostMapping("/OrderProc/{id}")
    public String cartCheckout(@PathVariable("id") Integer id, 
    							@AuthenticationPrincipal PrincipalDetails principalDetails, 
    							Model model) {
        // 로그인이 되어있는 유저의 id와 주문하는 id가 같아야 함
        if(principalDetails.getUser().getId() == id) {
            UserVo user = userService.findUser(id);

            CartVo userCart = cartService.findUserCart(user.getId());

            // 유저 카트 안에 있는 상품들
            List<CartItemVo> userCartItems = cartService.allUserCartView(userCart);

            // 최종 결제 금액
            int totalPrice = 0;
            for (CartItemVo cartItem : userCartItems) {
                // 장바구니 안에 있는 상품의 재고가 없거나 재고보다 많이 주문할 경우
                if (cartItem.getItem().getStock() == 0 || cartItem.getItem().getStock() < cartItem.getCount()) {
                    return "redirect:/main";
                }
                totalPrice += cartItem.getCount() * cartItem.getItem().getItem_price();
            }

            // 유저 돈에서 최종 결제금액 빼야함

            List<OrderItem> orderItemList = new ArrayList<>();

            for (CartItemVo cartItem : userCartItems) {

                // 재고 감소
                cartItem.getItem().setStock(cartItem.getItem().getStock() - cartItem.getCount());

                // order, orderItem 에 담기
                OrderItem orderItem = orderService.addCartOrder(cartItem.getItem().getId(), user, cartItem);
                log.info("orderItem =====> "+orderItem);
                orderItemList.add(orderItem);
            }

            orderService.addOrder(user, orderItemList);

            // 장바구니 상품 모두 삭제
            cartService.allCartItemDelete(id);
            
            model.addAttribute("content", "user/Home");
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartItems", userCartItems);
            model.addAttribute("user", userService.findUser(id));

            return "user/Main";
        } else {
            return "redirect:/main";
        }
    }
}
