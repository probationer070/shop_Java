package com.demo.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartVo, Integer> {

	CartVo findByUserId(int id);

	CartVo findCartByUserId(int id);

}
