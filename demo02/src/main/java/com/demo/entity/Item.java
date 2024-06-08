//package com.demo.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Entity
//@Getter
//@ToString
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Item {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Column(name="item_name", nullable = false)
//	private String name;	// 상품 이름
//	
//	@Column(name="item_detail", nullable = false)
//	private String detail;	// 상품 설명
//	
//	@Column
//	private int item_price;	// 상품 가격
//	@Column
//	private int stock; // 재고
//	@Column
//	private int isSoldout; // 재고 없을 때 : 1
//	
//	@ManyToOne
//	@JoinColumn(name="user_id")
//	private User user;
//	
//	@OneToMany(mappedBy = "item")
//	private List<CartItem> cart_items = new ArrayList<>();
//	@Column
//	private String img;	// 상품 사진
//
//}
