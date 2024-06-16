package com.demo.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<CartVo, Integer> {

	CartVo findByUserId(Long id);

}
