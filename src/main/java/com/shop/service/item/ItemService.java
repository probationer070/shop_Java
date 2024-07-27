package com.shop.service.item;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.shop.vo.Item.ItemVo;

public interface ItemService {

	List<ItemVo> TotalitemView();

	ItemVo itemDetail(ItemVo ivo);

	ItemVo saveItem(ItemVo ivo, MultipartFile imgFile);

	int itemModify(ItemVo ivo, MultipartFile imgFile);

	int itemDelete(ItemVo ivo);

}
