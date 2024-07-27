package com.shop.vo.cartItem;

import com.shop.vo.Item.ItemVo;
import com.shop.vo.cart.CartVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name="CartItem")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="item_id")
	private ItemVo item;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="cart_id")
	private CartVo cart;
	
	private int count; // 상품 개수
	
	public static CartItemVo createCartItem(CartVo cart, ItemVo item, int amount) {
		CartItemVo cartItem = new CartItemVo();
		cartItem.setCart(cart);
		cartItem.setItem(item);
		cartItem.setCount(amount);
		return cartItem;
	}
	
	public void addCount(int count) {
		this.count += count;
	}
}
