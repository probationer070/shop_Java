package com.shop.vo.user;

import java.time.LocalDateTime;

import com.shop.vo.cart.CartVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="User")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true) // username 중목 안됨
	private String username;
	
	private String email;
	private String password;
	private String address;
	
	// 로그인 횟수, 잠금 시간, 수정시간 생각 필요
	
	private String role; // 권한

    private LocalDateTime createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @OneToOne(mappedBy = "user")
    private CartVo cart;
}
