package com.shop.vo.cartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItemVo, Integer> {

	CartItemVo findByCartIdAndItemId(int id, int i);
	CartItemVo findCartItemById(int id);
    List<CartItemVo> findCartItemByItemId(int id);
}
