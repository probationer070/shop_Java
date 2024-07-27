package com.shop.vo.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.shop.vo.cartItem.CartItemVo;
import com.shop.vo.user.UserVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="Item")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ItemVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String kind;	// 상품 종류
	
	private String name;	// 상품 이름
	
	private String detail;	// 상품 설명
	
	private int item_price;	// 상품 가격
	
	private int stock; // 재고
	
	private int isSoldout; // 재고 없을 때 : 1
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserVo user;	// 판매자 아이디
	

	@OneToMany(mappedBy="item") 
	private List<CartItemVo> cart_items = new ArrayList<>(); 
	
	private String imgName; // 상품 사진
	private String imgPath;	
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 상품 등록 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }
	
	
}
