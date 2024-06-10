package com.demo.domain.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.domain.cart.Cart;

public interface CartItemRepo extends JpaRepository<Cart, Integer> {
	
}
