package com.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.domain.Item.ItemVo;
import com.demo.domain.Item.ItemRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	@Autowired
	final ItemRepo itemRepo;
	
	// 수정 및 보완 필요
	public ItemVo saveItem(ItemVo item, MultipartFile imgFile) throws Exception {
		String oriImgName = imgFile.getOriginalFilename();
		String imgName = "";
		String projectPath = item.getImgPath();
		
		UUID uuid = UUID.randomUUID();
		
		String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        item.setImgName(imgName);
        item.setImgPath("/uploads/" + imgName);
		
		return itemRepo.save(item);
	}

	public List<ItemVo> TotalitemView() {
		return itemRepo.findAll();
	}

	public ItemVo itemView(int id) {
		return itemRepo.findById(id).get();
	}
	
	@Transactional
	public void itemDelete(Integer id) {
		itemRepo.deleteById(id);
	}
	
	public void itemModify(ItemVo item, MultipartFile imgFile) {
		ItemVo update = itemRepo.findItemById(item.getId());
		update.setIsSoldout(item.getIsSoldout());
		update.setKind(item.getKind());
		update.setName(item.getName());
		update.setDetail(item.getDetail());
		update.setItem_price(item.getItem_price());
		update.setStock(item.getStock());
		// update.setImgPath(item.getImgPath());
		itemRepo.save(update);
	}

	public Map<String, Object> getItemList() {
		// TODO Auto-generated method stub
		return null;
	}
}
