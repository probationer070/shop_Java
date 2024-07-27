package com.shop.service.item;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dao.item.ItemDao;
/*import com.shop.vo.Item.ItemRepo;*/
import com.shop.vo.Item.ItemRepo;
import com.shop.vo.Item.ItemVo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemRepo itemRepo;

	 
	  // 수정 및 보완 필요 
//	public int saveItem(ItemVo item, MultipartFile imgFile) throws Exception { 
//		
//	  String oriImgName = imgFile.getOriginalFilename(); 
//	  String imgName= ""; 
//	  String projectPath = item.getImgPath();
//	  
//	  UUID uuid = UUID.randomUUID();
//	  
//	  String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName
//	  
//	  imgName = savedFileName;
//	  
//	  File saveFile = new File(projectPath, imgName);
//	  
//	  imgFile.transferTo(saveFile);
//	  
//	  item.setImgName(imgName); item.setImgPath("/uploads/" + imgName);
//	  
//	  return itemDao.saveItem(item);
//	  }
	 

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

	@Override
	public ItemVo itemDetail(ItemVo ivo) {
		log.info("ivo ++++++++ " + ivo);
		return itemRepo.findItemById(ivo.getId());
	}

	@Override
	public ItemVo saveItem(ItemVo ivo, MultipartFile imgFile) {
		String oriImgName = imgFile.getOriginalFilename(); 
		String projectPath = ivo.getImgPath();
		if (oriImgName==null || oriImgName.length()==0) {
			ivo.setImgName("loading.gif");
			ivo.setImgPath("/images/loading.gif");
		} else {			
			UUID uuid = UUID.randomUUID();
			String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName
			File saveFile = new File(projectPath, savedFileName);
			try {				
				imgFile.transferTo(saveFile);
				ivo.setImgName(savedFileName); 
				ivo.setImgPath("/uploads/" + savedFileName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return itemRepo.save(ivo);
	}

	@Override
	public int itemModify(ItemVo ivo, MultipartFile imgFile) {
		String oriImgName = imgFile.getOriginalFilename();
		File destinationFile;
		if (ivo.getImgName().equals("loading.gif") || ivo.getImgName()==null) {
			if (oriImgName==null || oriImgName.length()==0)
				ivo.setImgName("loading.gif");
		} else {			
			if (oriImgName!=null || oriImgName.length()>0) {
				UUID uuid = UUID.randomUUID();
				String savedFileName = uuid + "_" + oriImgName;
				ivo.setImgName(savedFileName);
				ivo.setImgPath("/uploads/" + savedFileName);
				destinationFile = new File(ivo.getImgPath()+oriImgName);
				// 실제 파일 전송
				try {
					imgFile.transferTo(destinationFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return itemDao.itemModify(ivo);
	}

	@Override
	public int itemDelete(ItemVo ivo) {
		return itemDao.itemDelete(ivo);
	}

	

	
//	 public int itemModify(ItemVo item, MultipartFile imgFile) { 
//		 ItemVo update = itemRepo.findItemById(item.getId());
//		 update.setIsSoldout(item.getIsSoldout()); 
//		 update.setKind(item.getKind());
//		 update.setName(item.getName()); 
//		 update.setDetail(item.getDetail());
//		 update.setItem_price(item.getItem_price()); 
//		 update.setStock(item.getStock());
//		 update.setImgPath(item.getImgPath()); 
//		 itemRepo.save(update); 
//	}
	 

}
