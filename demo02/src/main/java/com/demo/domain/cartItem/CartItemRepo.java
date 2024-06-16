package com.demo.domain.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItemVo, Integer> {

	CartItemVo findByCartIdAndItemId(int id, Long id2);
	
}
