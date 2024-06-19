package com.demo.domain.Item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<ItemVo, Integer> {

	void deleteById(Integer id);

	ItemVo findItemById(int id);

	

}
