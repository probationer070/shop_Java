package com.shop.dao.item;

import org.apache.ibatis.annotations.Mapper;

import com.shop.vo.Item.ItemVo;

@Mapper
public interface ItemDao {

	int saveItem(ItemVo ivo);

	int itemModify(ItemVo ivo);

	int itemDelete(ItemVo ivo);

}
