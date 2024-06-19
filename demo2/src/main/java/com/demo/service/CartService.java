package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.Item.ItemRepo;
import com.demo.domain.Item.ItemVo;
import com.demo.domain.cart.CartRepo;
import com.demo.domain.cart.CartVo;
import com.demo.domain.cartItem.CartItemRepo;
import com.demo.domain.cartItem.CartItemVo;
import com.demo.domain.user.UserVo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartItemRepo cartItemRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	/*
	 * @Transactional public void addCart(UserVo user) { CartVo cart =
	 * cartRepo.findByUserId(user.getId());
	 * 
	 * if(cart == null) { cart = CartVo.createCart(user); cartRepo.save(cart); } }
	 */
	
	@Transactional
	public void addCart(UserVo user, ItemVo newitem, int amount) {
		// Search Cart by user id
		CartVo cart = cartRepo.findByUserId(user.getId());
		
		if(cart == null) {
			cart = CartVo.createCart(user);
			cartRepo.save(cart);
		}
		
		ItemVo item = itemRepo.findItemById(newitem.getId());
		CartItemVo cartItem = cartItemRepo.findByCartIdAndItemId(cart.getId(), item.getId());
		
		if (cartItem == null) {
            cartItem = CartItemVo.createCartItem(cart, item, amount);
            cartItemRepo.save(cartItem);
        }
		
		else {
			CartItemVo update = cartItem;
			update.setCart(cartItem.getCart());
            update.setItem(cartItem.getItem());
            update.addCount(amount);
            update.setCount(update.getCount());
			cartItemRepo.save(update);
		}
		
		cart.setCount(cart.getCount()+amount);
	}

	public List<CartItemVo> allUserCartView(CartVo userCart) {
        int userCartId = userCart.getId();

        List<CartItemVo> UserCartItems = new ArrayList<>();
        List<CartItemVo> CartItems = cartItemRepo.findAll();

        for(CartItemVo cartItem : CartItems) {
            if(cartItem.getCart().getId() == userCartId) {
                UserCartItems.add(cartItem);
            }
        }

        return UserCartItems;
	}

	public CartItemVo findCartItemById(int itemId) {
		CartItemVo cartItem = cartItemRepo.findCartItemById(itemId);

        return cartItem;
	}

	public CartVo findUserCart(int id) {
		return cartRepo.findCartByUserId(id);
	}

	public void cartItemDelete(int itemId) {
		cartItemRepo.deleteById(itemId);
	}

	public void allCartItemDelete(int id) {
		List<CartItemVo> cartItems = cartItemRepo.findAll();

        // 반복문을 이용하여 해당하는 User 의 CartItem 만 찾아서 삭제
        for(CartItemVo cartItem : cartItems){

            if(cartItem.getCart().getUser().getId() == id) {

                cartItem.getCart().setCount(0);

                cartItemRepo.deleteById(cartItem.getId());
            }
        }
		
	}

}
