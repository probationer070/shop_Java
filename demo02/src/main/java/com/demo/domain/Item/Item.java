package com.demo.domain.Item;

import java.util.ArrayList;
import java.util.List;

import com.demo.domain.cartItem.CartItem;
import com.demo.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String kind;	// 상품 종류
	
	private String name;	// 상품 이름
	
	private String detail;	// 상품 설명
	
	private String item_price;	// 상품 가격
	
	private int stock; // 재고
	
	private int isSoldout; // 재고 없을 때 : 1
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;	// 판매자 아이디
	

	@OneToMany(mappedBy = "item") 
	private List<CartItem> cart_items = new ArrayList<>(); 
	
	private String imgName; // 상품 사진
	private String imgPath;
	
	
}
