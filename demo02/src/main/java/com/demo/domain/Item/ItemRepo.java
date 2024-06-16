package com.demo.domain.Item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<ItemVo, Long> {

	void deleteById(Integer id);

	ItemVo findItemById(Long id);

	

}
