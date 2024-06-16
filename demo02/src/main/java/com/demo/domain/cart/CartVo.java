package com.demo.domain.cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.format.annotation.DateTimeFormat;

import com.demo.domain.cartItem.CartItemVo;
import com.demo.domain.user.UserVo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name="Cart")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserVo user;
	
	private int count;	// 카트 담긴 상품 수
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItemVo> cart_items = new ArrayList<>();
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDate.now();
	}
	
	public static CartVo createCart(UserVo user) {
		CartVo cart = new CartVo();
		cart.setCount(0);
		cart.setUser(user);
		return cart;
	}
}
